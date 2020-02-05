using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Runtime;
using Android.Widget;
using Android.Bluetooth;
using System.IO;
using Java.Util;
using System.Threading.Tasks;
using System;
using System.Text;

namespace OSID
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        //Variables para el manejo del bluetooth Adaptador y Socket
        private BluetoothAdapter mBluetoothAdapter = null;
        private BluetoothSocket btSocket = null;
        //Streams de lectura I/O
        private Stream outStream = null;
        private Stream inStream = null;
        //MAC Address del dispositivo Bluetooth
        private static string address = "98:D3:32:31:5C:28";
        //Id Unico de comunicacion
        private static UUID MY_UUID = UUID.FromString("00001101-0000-1000-8000-00805F9B34FB");

        TextView glucoseText;
        TextView voltageText;

        ToggleButton conectar;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);

            glucoseText = FindViewById<TextView>(Resource.Id.glucoseText);
            voltageText = FindViewById<TextView>(Resource.Id.voltageText);
            conectar = FindViewById<ToggleButton>(Resource.Id.toggleButton1);

            conectar.CheckedChange += tgConnect_HandleCheckedChange;

            CheckBt();
        }
        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {
            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);

            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        //Metodo de verificacion del sensor Bluetooth
        private void CheckBt()
        {
            //asignamos el sensor bluetooth con el que vamos a trabajar
            mBluetoothAdapter = BluetoothAdapter.DefaultAdapter;

            //Verificamos que este habilitado
            if (!mBluetoothAdapter.Enable())
            {
                Toast.MakeText(this, "Bluetooth Desactivado",
                    ToastLength.Short).Show();
            }
            //verificamos que no sea nulo el sensor
            if (mBluetoothAdapter == null)
            {
                Toast.MakeText(this,
                    "Bluetooth No Existe o esta Ocupado", ToastLength.Short)
                    .Show();
            }
        }

        void tgConnect_HandleCheckedChange(object sender, CompoundButton.CheckedChangeEventArgs e)
        {
            if (e.IsChecked)
            {
                //si se activa el toggle button se incial el metodo de conexion
                ConnectAsync();
            }
            else
            {
                //en caso de desactivar el toggle button se desconecta del arduino
                if (btSocket.IsConnected)
                {
                    try
                    {
                        btSocket.Close();
                    }
                    catch (System.Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }
            }
        }

        //Evento de conexion al Bluetooth
        public async Task ConnectAsync()
        {
            //Iniciamos la conexion con el arduino
            BluetoothDevice device = mBluetoothAdapter.GetRemoteDevice(address);
            System.Console.WriteLine("Conexion en curso" + device);

            //Indicamos al adaptador que ya no sea visible
            mBluetoothAdapter.CancelDiscovery();
            try
            {
                //Inicamos el socket de comunicacion con el arduino
                btSocket = device.CreateRfcommSocketToServiceRecord(MY_UUID);
                //Conectamos el socket
                //btSocket.Connect();
                await btSocket.ConnectAsync();
                System.Console.WriteLine("Conexion Correcta");
            }
            catch (System.Exception e)
            {
                //en caso de generarnos error cerramos el socket
                Console.WriteLine(e.Message);
                try
                {
                    btSocket.Close();
                }
                catch (System.Exception)
                {
                    System.Console.WriteLine("Imposible Conectar");
                }
                System.Console.WriteLine("Socket Creado");
            }

            //glucoseText.Text = string.Format("{0:N3}", CalculateGlucoseConcentration(ClearData(beginListenForData()))) + " mg/dL";
            voltageText.Text = beginListenForData();
            glucoseText.Text = CalculateGlucoseConcentration(ClearData(voltageText.Text)) + " mg/dL";
        }
     
        //Evento para inicializar el hilo que escuchara las peticiones del bluetooth
        public string  beginListenForData()
        {
            //Extraemos el stream de entrada
            try
            {
                inStream = btSocket.InputStream;
            }
            catch (System.IO.IOException ex)
            {
                Console.WriteLine(ex.Message);
            }
            //Creamos un hilo que estara corriendo en background el cual verificara si hay algun dato
            //por parte del arduino
            byte[] buffer = new byte[4];
            //string resultado = "";
            inStream.Read(buffer, 0, buffer.Length);

           return Encoding.ASCII.GetString(buffer);
        }
        public float ClearData(string data)
        {
            // transformar string to float y cortar los datos hasta donde aparezca el caracter "|"
            return float.Parse(data);
        }
        public double CalculateGlucoseConcentration(float voltage)
        {
            //implementar algoritmo
            //(((2347110.773 * (18.06302774 * 0.001) ** (value) + (2 * (4.65 - value))) / (8 * value)) ** 2.2) + (10 * value) + 30 - value
            //volatge  = 2.56
            /*double a = 2347110.773 * (18.06302774 * 0.001);//42395.927
            double b = (2 * (4.65 - voltage));//4.18
            double c = Math.Pow(a, voltage);// 7.0 x10 ala 11
            double d = c + b;
            double e = (8 * voltage);//
            double f = d / e;
            double g = Math.Pow(f, 2.2f);
            double h = (10 * voltage);
            double i = 30 - voltage;
            double result = g + h + i;*/
            //Math.Pow(((2347110.773 * Math.Pow((18.06302774 * 0.001), (voltage)) + (2 * (4.65 - voltage))) / (8 * voltage)) ,2.2f) + (10 * voltage) + 30 - voltage
            return Math.Round(Math.Pow(((2347110.773 * Math.Pow((18.06302774 * 0.001), (voltage)) + (2 * (4.65 - voltage))) / (8 * voltage)), 2.2f) + (10 * voltage) + 30 - voltage);
        }
    }
}