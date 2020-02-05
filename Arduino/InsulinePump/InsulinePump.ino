#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>

//Declaremos los pines CE y el CSN
#define CE_PIN 7
#define CSN_PIN 8

const byte address[6] = "00001";

RF24 radio(CE_PIN, CSN_PIN);
float datos[2];

void setup() {
  Serial.begin(9600);
  radio.begin();
  radio.openReadingPipe(0, address);
  radio.setPALevel(RF24_PA_MIN);
  radio.startListening();

}

void loop() {
  uint8_t numero_canal;
  if (radio.available() )
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
  float f = datos[1];
     String fstring = String(f) + "|";
     writeString(fstring);

     delay(1000);
}
void writeString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
  {
    Serial.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
  }

}// end writeString
