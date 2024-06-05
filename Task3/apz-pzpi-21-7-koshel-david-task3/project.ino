#include "DHT.h"
#include <WiFi.h>
#include "ThingSpeak.h"
#define DHTPIN 0
#define LDRPIN 32
#define DHTTYPE DHT22

const int channelNumber = 2559929 ;
const char* writeKey = "GFHNICEOUA6OY0ZI";

WiFiClient client;
DHT dht(DHTPIN,DHTTYPE);


void setup() {
  Serial.begin(9600);
  pinMode(LDRPIN, INPUT);
  dht.begin();
  WiFi.begin("Wokwi-GUEST", "");

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");
  WiFi.mode(WIFI_STA);
  ThingSpeak.begin(client);
}

void loop() { 
  // Зчитування та відправка показників датчиків до ThingSpeak
  int l = analogRead(LDRPIN);
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  ThingSpeak.setField(1,t);
  ThingSpeak.setField(2,h);
  ThingSpeak.setField(3,l);

  if (isnan(h) || isnan(t)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  Serial.print(F("Humidity: "));
  Serial.print(h);
  Serial.print(F("%  Temperature: "));
  Serial.print(t);
  Serial.println(F("°C "));
  Serial.println(l);

  int x = ThingSpeak.writeFields(channelNumber,writeKey);
  if(x == 200){
    Serial.println("Data pushed successfull");
  }else{
    Serial.println("Push error" + String(x));
  }

  delay(10000);
}
