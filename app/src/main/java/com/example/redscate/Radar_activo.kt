package com.example.redscate

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.redscate.Constants.TAG
import com.example.redscate.Constants.TAG_WIFI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class Radar_activo : AppCompatActivity(), SensorEventListener {
    //checks

    private lateinit var check1: ImageView
    private lateinit var check2: ImageView
    private lateinit var check3: ImageView
    private lateinit var tresPuntos: ImageView
    private lateinit var tresPuntos2: ImageView

    private lateinit var mensajeE: TextView

    private lateinit var checkContainer: LinearLayout
    private lateinit var checkContainer_2: LinearLayout
    private lateinit var distanciaMensaje: TextView
    private lateinit var sonido: ImageView

    //sonido
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var reproduciendo = false

    //variables de brujula
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)
    private lateinit var compassImageView: ImageView

    private var currentAzimuth = 0f

    //WiFi Direct variables
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var wifiManager: WifiManager
    private lateinit var mManager: WifiP2pManager
    private lateinit var mChannel: WifiP2pManager.Channel


    private var deviceArray: MutableList<MessageModel> = mutableListOf()


    private val connectedDevices: MutableList<WifiP2pDevice> = mutableListOf()
    private var info: WifiFrame = WifiFrame()
    private var selectedDevice: WifiP2pDevice? = null

    private var messages: MutableList<String> = mutableListOf()
    //private lateinit var messageAdapter: MessageAdapter


    var text: CharSequence = "Activa el wifi"
    var duration = Toast.LENGTH_SHORT
    private lateinit var locationManager: LocationManager
    private lateinit var txtUbicacion: TextView
    private val PERMISO_LOCATION = 100

    private val handle = Handler(Looper.getMainLooper())
    private val intervalo: Long = 30000 // 30 segundos

    private lateinit var mensajeEnviado: TextView

    private fun enableEdgeToEdge() {
        // Implementa la lógica de configuración de pantalla completa si es necesaria
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_radar_activo)

        // Configurar ventanas en pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //brujula
        compassImageView = findViewById(R.id.compas_imageView) // La imagen que se va a rotar
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        //red
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_KEY, MODE_PRIVATE)

        //txtUbicacion = findViewById(R.id.txtUbicacion)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISO_LOCATION
            )
            return
        }

        iniciarActualizaciones()

        val permission = Manifest.permission.NEARBY_WIFI_DEVICES

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido: inicia la detección de dispositivos
            } else {
                // Permiso denegado
            }
        }
        init()
        addServiceRequest()
        iniciarDescubrimientoRepetido() //envia mensajes automaticamente

        //startTimer()
        startDiscover()

        //messageAdapter = MessageAdapter(messages)

        exqListener()
        //binding.peerListView.setOnItemClickListener { parent, viiew, pos, id ->
        //  selectedDevice = deviceArray[pos].device
        startTimer()

        // botones
        val clasecancelar = Cancelar(this)
        val claseAutoprotecicon = Autoproteccion(this)
        val autoproteccion = findViewById<LinearLayout>(R.id.btn_autoproteccion)
        val cancelar = findViewById<LinearLayout>(R.id.btn_detener)
        cancelar.setOnClickListener {
            clasecancelar.showPopupWindowCancelar(it)
        }
        autoproteccion.setOnClickListener {
            claseAutoprotecicon.showPopupWindowAutoproteccion(it)
        }

    }

    //inicio brujula
    override fun onResume() {
        super.onResume()
        accelerometer?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
        }
        magnetometer?.also { mag ->
            sensorManager.registerListener(this, mag, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this) // Desregistra los sensores
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, gravity, 0, event.values.size)
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, geomagnetic, 0, event.values.size)
            }
        }

        val R = FloatArray(9)
        val I = FloatArray(9)
        if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(R, orientation)
            val azimuthInRadians = orientation[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).roundToInt()

            // Animación de rotación de la brújula
            val rotateAnimation = RotateAnimation(
                currentAzimuth,
                (-azimuthInDegrees).toFloat(),
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            rotateAnimation.duration = 500
            rotateAnimation.fillAfter = true

            compassImageView.startAnimation(rotateAnimation)
            currentAzimuth = (-azimuthInDegrees).toFloat()


            // Mostrar dirección en texto
            val direction = when {
                azimuthInDegrees >= 337 || azimuthInDegrees <= 23 -> "Norte"
                azimuthInDegrees in 24..68 -> "Noreste"
                azimuthInDegrees in 69..113 -> "Este"
                azimuthInDegrees in 114..158 -> "Sureste"
                azimuthInDegrees in 159..203 -> "Sur"
                azimuthInDegrees in 204..248 -> "Suroeste"
                azimuthInDegrees in 249..293 -> "Oeste"
                azimuthInDegrees in 294..336 -> "Noroeste"
                else -> "Desconocido"
            }

        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se necesita implementar aquí para esto
    }
    //fin brujula

    //inicio red

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun iniciarActualizaciones() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            15000L, // Cada 5 segundos
            0f,    // Distancia mínima (0 metros)
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    lat = location.latitude
                    lon = location.longitude
                    //txtUbicacion.text = "Lat: $lat\nLon: $lon"
                }

                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            },
            Looper.getMainLooper()
        )
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISO_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarActualizaciones()
        }
    }

    fun iniciarDescubrimientoRepetido() {
        handle.post(discoverRunnabl)
    }

    private val discoverRunnabl = object : Runnable {
        override fun run() {
            exqListener()
            handle.postDelayed(this, intervalo)
        }
    }
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var task: Runnable? = null
    private var interval: Long = 5000
    private val devicesWithReceivedMessages: MutableSet<String> = mutableSetOf()

    //Realiza la busqueda de servicios cada x tiempo
    fun startDiscover() {
        // Crea un nuevo Runnable que se ejecutará después de cada intervalo
        task = Runnable {
            discoverServices()
            clearReceivedDevicesAfterDelay()

        }
        // Programa el primer ciclo del temporizador con un retraso inicial de 0 y un intervalo especificado
        executor.scheduleAtFixedRate(task!!, 0, interval, TimeUnit.MILLISECONDS)
    }

    private fun startTimer() {
        executor.scheduleAtFixedRate({

            selectedDevice?.let { device ->
                // Buscar el índice del dispositivo seleccionado en la lista
                val index = deviceArray.indexOfFirst { it.device.deviceName == device.deviceName }
                if (index != -1) {
                    // Llamar a updateDescription con el índice del dispositivo seleccionado
                    if (!devicesWithReceivedMessages.contains(device.deviceName)) {
                        // Si es la primera vez que recibes un mensaje del dispositivo, procésalo
                        runOnUiThread {
                            // Actualiza la descripción o realiza las operaciones necesarias con el dispositivo
                            updateDescription(index)
                        }
                        // Marca el dispositivo como uno del que ya has recibido un mensaje
                        devicesWithReceivedMessages.add(device.deviceName)
                    }
                }
            }
        }, 0, interval, TimeUnit.MILLISECONDS)
    }

    private fun clearReceivedDevicesAfterDelay() {
        // Limpia el conjunto de dispositivos después de cierto tiempo (por ejemplo, 30 segundos)
        executor.schedule({
            devicesWithReceivedMessages.clear()
        }, 500, TimeUnit.MILLISECONDS)
    }

    // Guarda el mensaje y lo envia al momento de oprimir el botón
    fun exqListener() {
        tresPuntos = findViewById(R.id.tres_puntos_1)
        tresPuntos2 = findViewById(R.id.tres_puntos_2)
        check1 = findViewById(R.id.check_1)
        check2 = findViewById(R.id.check_2)
        check3 = findViewById(R.id.check_3)

        check1.setImageResource(R.drawable.check_verde)
        check2.setImageResource(R.drawable.reloj)
        check3.setImageResource(R.drawable.check_gris)
        tresPuntos.setImageResource(R.drawable.tres_puntos_verdes)
        tresPuntos2.setImageResource(R.drawable.tres_puntos_amarrillo)
        mensajeEnviado = findViewById(R.id.mensaje_enviado)
        mensajeEnviado.text = "Buscando rescatistas cercanos"
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val nombre = sharedPreferences.getString("nombre", "Valor por defecto")
        var message = "0,$lat,$lon,$nombre"
        //Toast.makeText(applicationContext, "Mensaje enviado" + message, Toast.LENGTH_LONG).show()

        messages.add(message.trim() + " - Hora de envío: " + getFormattedDateTime())
        saveMessage(message)

        clearLocalServices {
            startRegistration()
        }

    }

    //Configuración para utilizar los servicio de WifiP2p
    fun init() {

        wifiManager = this.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        mManager = getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, mainLooper, null)

        WifiFrameUtils.getUUIDWiFiFrame(this)


    }

    private fun addDeviceList(record: WifiP2pDevice, wifiFrame: WifiFrame) {

        val deviceSame = deviceArray.firstOrNull { it.device.deviceName == record.deviceName }

        if (deviceSame != null) {
            val messageExist = deviceSame.message.any {
                it.dateSend == wifiFrame.dateSend
            }

            if (!messageExist)
                deviceSame.message.add(wifiFrame)

        } else {

        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            deviceArray

        )
        //binding.peerListView.adapter = adapter
        //multihop(WifiFrameUtils.deviceIdMultiHop)
    }


    private fun updateDescription(selectedDevice: Int) {

        val selectedDeviceInfo = deviceArray[selectedDevice]
        //binding.messageTextView.text = selectedDeviceInfo.getAllMessage()

    }

    //Agregando servicio local para ofrecer la información del dispositivo actual a traves del servicio
    private fun startRegistration() {


        info = WifiFrameUtils.buildMyWiFiFrame(this)

        val record = WifiFrameUtils.wifiFrameToHashMap(info)


        // Service information.  Pass it an instance name, service type
        val serviceInfo =
            WifiP2pDnsSdServiceInfo.newInstance("_redscate", "_chatApp._tcp", record)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED
                    )
        ) {

            tresPuntos = findViewById(R.id.tres_puntos_1)
            tresPuntos2 = findViewById(R.id.tres_puntos_2)
            check1 = findViewById(R.id.check_1)
            check2 = findViewById(R.id.check_2)
            check3 = findViewById(R.id.check_3)
            check1.setImageResource(R.drawable.check_gris)
            check2.setImageResource(R.drawable.check_gris)
            check3.setImageResource(R.drawable.check_gris)
            tresPuntos.setImageResource(R.drawable.tres_puntos_gris)
            tresPuntos2.setImageResource(R.drawable.tres_puntos_gris)
            mensajeEnviado = findViewById(R.id.mensaje_enviado)

            mensajeEnviado.text = "Falta permiso de dispositivos cerca\nPor favor Activarlo!!! Y VUELVE A ABRIR LA APLICACION"
            /*Toast.makeText(
                this,
                ,
                Toast.LENGTH_SHORT
            ).show()*/
            return
        }
        mManager.addLocalService(mChannel, serviceInfo, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {

            }

            override fun onFailure(arg0: Int) {
                // Command failed.  Check for P2P_UNSUPPORTED,  , or BUSY
                mensajeEnviado = findViewById(R.id.mensaje_enviado)
                mensajeEnviado.text = "Falla el servicio local"
                //Toast.makeText(this@MainActivity, "Fail local service", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Configura la solicitud para realizar el descubrimiento e informa si la solictud se realizo correctamente
    private fun addServiceRequest() {
        val serviceRequest = WifiP2pDnsSdServiceRequest.newInstance(
            "_redscate",
            "_chatApp._tcp"
        )

        mManager.addServiceRequest(
            mChannel,
            serviceRequest,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    discoverListener()

                }

                override fun onFailure(code: Int) {
                    tresPuntos = findViewById(R.id.tres_puntos_1)
                    tresPuntos2 = findViewById(R.id.tres_puntos_2)
                    check1 = findViewById(R.id.check_1)
                    check2 = findViewById(R.id.check_2)
                    check3 = findViewById(R.id.check_3)
                    check1.setImageResource(R.drawable.check_gris)
                    check2.setImageResource(R.drawable.check_gris)
                    check3.setImageResource(R.drawable.check_gris)
                    tresPuntos.setImageResource(R.drawable.tres_puntos_gris)
                    tresPuntos2.setImageResource(R.drawable.tres_puntos_gris)
                    mensajeEnviado = findViewById(R.id.mensaje_enviado)
                    mensajeEnviado.text = "No se puede enviar los datos \n POR FAVOR ENCIENDE EL WI FI Y VUELVE A ABRIR LA APLICACION"
                    //Toast.makeText(this@MainActivity, "Failure addService", Toast.LENGTH_SHORT).show()
                    Log.e(TAG_WIFI, "Add service request has failed. $code")
                }
            }
        )
    }


    //Gestiona la información de los servicios recibidos
    private fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val locationA = Location("punto A").apply {
            latitude = lat1
            longitude = lon1
        }

        val locationB = Location("punto B").apply {
            latitude = lat2
            longitude = lon2
        }

        val distanciaMetros = locationA.distanceTo(locationB)

        return distanciaMetros
    }

    private fun discoverListener() {

        tresPuntos = findViewById(R.id.tres_puntos_1)
        tresPuntos2 = findViewById(R.id.tres_puntos_2)
        check1 = findViewById(R.id.check_1)
        check2 = findViewById(R.id.check_2)
        check3 = findViewById(R.id.check_3)

        val txtListener = WifiP2pManager.DnsSdTxtRecordListener { fullDomain, record, srcDevice ->
            Log.d(TAG, "DnsSdTxtRecord available -$record")
            val wifiFrame = WifiFrameUtils.hashMapToWiFiFrame(record)
            if (record.isEmpty()) return@DnsSdTxtRecordListener

            addDeviceList(srcDevice, wifiFrame)


            //Toast.makeText(this, "Servicio encontrado : mensaje ${wifiFrame.sendMessage}" , Toast.LENGTH_SHORT ).show()


            var partes = wifiFrame.sendMessage.split(",")
            for (parte in partes) {
                println(parte)
            }
            var rol = partes[0]
            var lat_1 = partes[1].toDouble()
            var lon_1 = partes[2].toDouble()
            var nombre_1 = partes[3]

            var distancia = calcularDistancia(lat, lon, lat_1, lon_1)
            if (lat_1 == 0.0){
                distancia = 300.0f
            }

            var imgBrujula = findViewById<ImageView>(R.id.compas_imageView)

            var textos = listOf(
                findViewById<TextView>(R.id.text_50),
                findViewById<TextView>(R.id.text_100),
                findViewById<TextView>(R.id.text_150),
                findViewById<TextView>(R.id.text_200)
            )

            var imagenes = listOf(
                findViewById<ImageView>(R.id.image50),
                findViewById<ImageView>(R.id.image100),
                findViewById<ImageView>(R.id.image150),
                findViewById<ImageView>(R.id.image200)
            )

            var nivelActivo = when {
                distancia in 1.0..50.0 -> 0
                distancia in 51.0..100.0 -> 1
                distancia in 101.0..150.0 -> 2
                distancia >= 151.0 -> 3
                else -> -1
            }

            if (rol == "1") {

                check1.setImageResource(R.drawable.check_verde)
                check2.setImageResource(R.drawable.check_verde)
                check3.setImageResource(R.drawable.check_verde)
                tresPuntos.setImageResource(R.drawable.tres_puntos_verdes)
                tresPuntos2.setImageResource(R.drawable.tres_puntos_verdes)

                mensajeE = findViewById(R.id.mensaje_enviado)
                nombre_1.uppercase()
                mensajeE.text = "Rescatista $nombre_1 encontrado"
                if (nivelActivo != -1) {
                    textos.forEachIndexed { index, textView ->
                        if (index == nivelActivo) {
                            textView.setBackgroundResource(
                                if (index == 3 || index == 1 || index == 0) R.drawable.rounded_distancia_verde
                                else R.drawable.rounded_distancia_verde
                            )
                            textView.setTextColor(ContextCompat.getColor(this, R.color.white_1))
                        } else {
                            textView.setBackgroundResource(R.drawable.rounded_distancia)
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    this,
                                    if (index == 2 && nivelActivo < 2) R.color.borde_boton_rojo else R.color.black_1
                                )
                            )
                        }
                    }

                    imagenes.forEachIndexed { index, imageView ->
                        imageView.setBackgroundResource(
                            if (index == nivelActivo) R.drawable.anillo_verde else R.drawable.anillo_blanci
                        )
                    }

                    imgBrujula.setImageResource(R.drawable.brujula_verde)
                }
                val cientocincuenta = findViewById<TextView>(R.id.text_150)
                if (lat_1 == 0.0 || lat == 0.0){
                    mensajeE.text = "Rescatista $nombre_1 encontrado \n NO ES POSIBLE CALCULAR SU DISTANCIA"
                }
                if (distancia < 101f || distancia > 150f) {
                    cientocincuenta.setTextColor(ContextCompat.getColor(this, R.color.black_1))
                }
                audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

                checkContainer = findViewById(R.id.check_container)
                checkContainer_2 = findViewById(R.id.check_container_2)
                distanciaMensaje = findViewById(R.id.distancia_mensaje)
                sonido = findViewById(R.id.sonido)

                if (distancia <= 50.0) {
                    checkContainer.visibility = View.GONE
                    checkContainer_2.visibility = View.VISIBLE
                    var dis = distancia.toInt()

                    distanciaMensaje.text = "RESCATISTA $nombre_1 A $dis METROS APROXIMADAMENTE"

                    sonido.setOnClickListener {
                        val volumenMaximo = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                        audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, volumenMaximo!!, 0)
                        if (!reproduciendo) {
                            // Iniciar sonido
                            mediaPlayer = MediaPlayer.create(this, R.raw.sonido)
                            mediaPlayer?.isLooping = true // Opcional: para que suene en bucle
                            mediaPlayer?.start()

                            // Establecer el volumen a un nivel alto
                            // El valor 1f es el volumen máximo (de 0.0 a 1.0)
                            mediaPlayer?.setVolume(1.0f, 1.0f)

                            reproduciendo = true
                        } else {
                            // Detener sonido
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            reproduciendo = false
                        }
                    }
                } else {
                    checkContainer.visibility = View.VISIBLE
                    checkContainer_2.visibility = View.GONE
                }
            } else{
                //Toast.makeText(this@Radar_activo, "Hay sobrevivientes cerca !!!", Toast.LENGTH_SHORT).show()

            }

            /*
                        Toast.makeText(
                            this,
                            "Servicio encontrado :esta a $distancia metros",
                            Toast.LENGTH_SHORT
                        ).show()
            */
        }

        val servListener =
            WifiP2pManager.DnsSdServiceResponseListener { instanceName, registrationType, resourceType ->
                Log.d("chat", "BonjourService available! instanceName: $instanceName")
                Log.d("chat", "BonjourService available! registrationType: $registrationType")
                Log.d("chat", "BonjourService available! resourceType: $resourceType")
            }


        mManager.setDnsSdResponseListeners(mChannel, servListener, txtListener)
    }

    private fun discoverPeers() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED)
        ) {

            Toast.makeText(this, "Faltan pemisos 3", Toast.LENGTH_SHORT).show()
            return
        }
        mManager.discoverPeers(
            mChannel,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    if (ActivityCompat.checkSelfPermission(
                            this@Radar_activo,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED ||
                        (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(
                            this@Radar_activo,
                            Manifest.permission.NEARBY_WIFI_DEVICES
                        ) != PackageManager.PERMISSION_GRANTED)
                    ) {

                        //Toast.makeText(this@MainActivity, "Faltan pemisos 7", Toast.LENGTH_SHORT).show()
                        return
                    }

                    //    mManager.requestPeers(mChannel, this@MainActivity)
                }

                override fun onFailure(error: Int) {
                    //Toast.makeText(this@MainActivity, "failure peers", Toast.LENGTH_SHORT).show()
                    Log.e(TAG_WIFI, "Discover peers has failed. $error")
                }
            })

    }

    private fun saveMessage(message: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.MESSAGE, message.trim())
        editor.apply()
    }

    //Inicia el descubrimiento
    private fun discoverServices() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED)
        ) {

            Toast.makeText(this, "Faltan pemisos 4", Toast.LENGTH_SHORT).show()
            return
        }
        mManager.discoverServices(
            mChannel,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    //Toast.makeText(this@MainActivity, "success discover", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(code: Int) {
                    //Toast.makeText(this@MainActivity, "Failure discover", Toast.LENGTH_SHORT).show()
                    Log.e(TAG_WIFI, "Discover services has failed. $code")
                }
            }
        )
    }

    private fun clearLocalServices(onSuccessCallback: () -> Unit) {
        mManager?.clearLocalServices(
            mChannel,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("success", "clearLocalServices result: Success")
                    // Toast.makeText(this@MainActivity,"Success clear local services", Toast.LENGTH_SHORT).show()
                    onSuccessCallback.invoke()
                }

                override fun onFailure(code: Int) {
                    /*
                    Log.e("Failed", "clearLocalServices result:  Failure with code $code")
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to clear local services: $code",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            })
    }
    // fin red
}