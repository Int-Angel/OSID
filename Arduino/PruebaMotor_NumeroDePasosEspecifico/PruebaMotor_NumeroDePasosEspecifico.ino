#include "RTClib.h"

const unsigned long SECOND = 1000;
const unsigned long HOUR = 3600*SECOND;
RTC_Millis RTC;
double counter = 0;

void setup() {
 Serial.begin(9600);
 RTC.begin(DateTime(__DATE__, __TIME__));
 pinMode(3, OUTPUT);
 pinMode(4, OUTPUT);
 pinMode(5, OUTPUT);
 pinMode(6, OUTPUT);
}

void loop() {
  display_time();
  InyectarInsulina(1); ///En unidades
  delay(1*HOUR);//Esperar 1 Hora
}

void InyectarInsulina(float unidades)
{
  int pasos = unidades * 66400;
  int vueltas = pasos / 8;
  
  for(int i=0; i<=vueltas; i++)
  {
     digitalWrite(3,HIGH);
     digitalWrite(4,LOW);
     digitalWrite(5,LOW);
     digitalWrite(6,HIGH);
     delay(3);
     
     digitalWrite(3,LOW);
     digitalWrite(4,LOW);
     digitalWrite(5,LOW);
     digitalWrite(6,HIGH);
     delay(3);
    
     digitalWrite(3,LOW);
     digitalWrite(4,LOW);
     digitalWrite(5,HIGH);
     digitalWrite(6, HIGH);
     delay(3);
    
      digitalWrite(3,LOW);
     digitalWrite(4,LOW);
     digitalWrite(5,HIGH);
     digitalWrite(6, LOW);
     delay(3);
    
     digitalWrite(3,LOW);
     digitalWrite(4,HIGH);
     digitalWrite(5,HIGH);
     digitalWrite(6, LOW);
     delay(3);
    
     digitalWrite(3,LOW);
     digitalWrite(4,HIGH);
     digitalWrite(5,LOW);
     digitalWrite(6, LOW);
     delay(3);
    
     digitalWrite(3,HIGH);
     digitalWrite(4,HIGH);
     digitalWrite(5,LOW);
     digitalWrite(6, LOW);
     delay(3);
     
     digitalWrite(3,HIGH);
     digitalWrite(4,LOW);
     digitalWrite(5,LOW);
     digitalWrite(6,LOW);
     delay(3);

     counter+=1;
    Serial.print(counter);
    Serial.println();
  }
}

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
