#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>
#include "RTClib.h"

//Declaremos los pines CE y el CSN
#define CE_PIN 7
#define CSN_PIN 8

const byte address[6] = "00001";

double counter = 0;
const unsigned long SECOND = 1000;
const unsigned long HOUR = 3600*SECOND;

unsigned long startMillis;
unsigned long currentMillis;
RTC_Millis RTC;

RF24 radio(CE_PIN, CSN_PIN);
float datos[2];
bool sendData;
void setup() {
  Serial.begin(9600);
  radio.begin();
  radio.openReadingPipe(0, address);
  radio.setPALevel(RF24_PA_MIN);
  radio.startListening();


  sendData = false;
  datos[0] = 0;
  datos[1] = 0;
  RTC.begin(DateTime(__DATE__, __TIME__));
  bool inyeccion = false;
}

void loop() {
  uint8_t numero_canal;
  if (radio.available() )
<<<<<<< Updated upstream
  {    
     //Leemos los datos y los guardamos en la variable datos[]
     radio.read(datos,sizeof(datos));
     
     //reportamos por el puerto serial los datos recibidos
     Serial.print("Dato0= " );
     Serial.print(datos[0]);
     Serial.println(" V, ");
     Serial.print("Dato1= " );
     Serial.print(datos[1]);
     Serial.println(" V, ");
    
 }
 if(!sendData && datos[1] != 0){
  
     float f = datos[1];
     String fstring = String(f) + "|";
     writeString(fstring);
     sendData = true;
     
   }
=======
  {
    //Leemos los datos y los guardamos en la variable datos[]
    radio.read(datos, sizeof(datos));
  }
  if (!sendData && datos[1] != 0)
  {

    float f = datos[1];
    String fstring = String(f);
    writeString(fstring);
    sendData = true;
    //Serial.print("enviado a bluethoot");

  }
  else if (!sendData)
    writeString("H");
>>>>>>> Stashed changes

    //---------------------INYECCION DE INSULINA--------------------------//
    if(Serial.available())//Sí existe conexion a la app..
    {
      int unidades = Serial.read();//Recibe las unidades mandadas por la app
      currentMillis = millis();
      display_time();//Se muestra la fecha y hora del inicio de la inyeccion
      InyectarInsulina(unidades); //Inyeccion
      delay(1 * HOUR); //Esperar 1 Hora
    }
    //--------------------------------------------------------------------//
}
<<<<<<< Updated upstream
void writeString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
=======

void InyectarInsulina(float unidades)
{
  int pasos = unidades * 66400;
  int vueltas = pasos / 8;

  for (int i = 0; i <= vueltas; i++)
>>>>>>> Stashed changes
  {
    digitalWrite(3, HIGH);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, HIGH);
    delay(3);

    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, HIGH);
    delay(3);

    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, HIGH);
    digitalWrite(6, HIGH);
    delay(3);

    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, HIGH);
    digitalWrite(6, LOW);
    delay(3);

    digitalWrite(3, LOW);
    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
    digitalWrite(6, LOW);
    delay(3);

    digitalWrite(3, LOW);
    digitalWrite(4, HIGH);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    delay(3);

    digitalWrite(3, HIGH);
    digitalWrite(4, HIGH);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    delay(3);

    digitalWrite(3, HIGH);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    delay(3);

    counter += 1;
    Serial.print(counter);
    Serial.println();
  }
}

void writeString(String stringData)
{ // Used to serially push out a String with Serial.write()
  
  Serial.flush();//borra lo que se haya mandado anteriormente por el puerto serial
  for (int i = 0; i < stringData.length(); i++)
    Serial.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
    
}// end writeString

void display_time() {
  DateTime now = RTC.now();
  Serial.print(now.year(), DEC);
  Serial.print('-');
  Serial.print(now.month(), DEC);
  Serial.print('-');
  Serial.print(now.day(), DEC);
  Serial.print(' ');
  Serial.print(now.hour(), DEC);
  Serial.print(':');
  Serial.print(now.minute(), DEC);
  Serial.print(':');
  Serial.print(now.second(), DEC);
  Serial.println();
}
