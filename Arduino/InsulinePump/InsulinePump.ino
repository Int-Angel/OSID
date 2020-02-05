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
  if ( radio.available() )
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
     Serial.write(1);
 }
}
