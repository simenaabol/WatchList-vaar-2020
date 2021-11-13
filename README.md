# WatchList AS
Prosjektet er starten på et produkt kalt WatchList, hvor det idéen er at man skal kombinere 
muligheten for deling av anbefalinger av filmer og serier, samtidig som man skal ha mange 
av de gjenkjennbare funksjonene til et sosialt medium. En bruker av WatchList skal kunne 
følge andre brukere og deretter kunne skrive og lese anbefalinger av filmer eller serier 
i ulike sjangre. 

## Motivasjon
Utviklingsteamet har i samråd med produkteier startet på en første-versjon av WatchList som
begge parter er svært fornøyd med. Det gjenstår fremdeles ikke-påbegynt funksjonalitet som 
trengs for at produktet skal fungere optimalt i henhold til produkteiers ønsker. 

Utviklingsteamets motivasjon for å vedlikeholde prosjektet er å tilfredsstille produkteiers
videre ønsker for utviklingen av produktet, samt å kontinuerlig kunne tilby ny funksjonalitet
som etterspørres av brukere. Det er viktig at produktet utvikles kontinuerlig for å møte
brukerens behov. 
 
 
 
 
## Spesielle egenskaper 
Gruppen bestemte seg tidlig at alt skulle lages fra bunnen av, og ikke bruke et ferdig rammeverk. 
Dette kalles full stack development og har noen fine fordeler. Blant annet gir det mer frihet til fremtidige utviklere av prosjektet da man ikke er låst til 
et spesielt rammeverk. Man har muligheten til å legge til et rammeverk, bytte ut SQL-databasen med en annen og all kode er åpen.


## Installasjon av nødvendig  programvare
 
For å kjøre produktet må det potensielt lastes ned seks  programmer. Det eksisterer også to substitutter for to av de nødvendige programmene, disse vil bli nevnt men installasjonen av disse vil ikke bli tatt med. Hvis du allerede har installert programvaren fra før av kan du hoppe over hele installasjonen.  Under ser du en oversikt over hvilke programvarer som er nødvendige. 
- Npm  eller yarn
- Node.js
- Java 11, 12 / OpenJDK 11, 12
- Gradle
- Cisco AnyConnect Secure Mobility Client (Tilgang til nettet til NTNU)
- Git (for Windows)

På bakgrunn av flertallet har Windows 10 som operativsystem vil dette i hovedsak benyttes i manualen. Der prosessen er annerledes for macOS Catalina og/eller Debian vil dette tas med.
Hvis du allerede har installert programvaren fra før av kan du hoppe over hele installasjonen. 

### Installasjon av npm og node.js

#### For Windows og Linux:
Gå inn på denne [nettsiden](https://nodejs.org/en/) og velg  “Recommended For Most Users” 
Det går helt fint om det har kommet en ny oppdatering med et annet versjon-navn. Det viktigste er å velge den der det står “Recommended For Most Users”. 
Etter å ha trykket på knappen velger man kjør, eventuelt lagre hvis man ikke får opp kjør først. Så trykker man Next, så huke av “I accept the terms in the License Agreement” og trykker Next fire ganger og så Install, videre Ja, og til slutt Finish. 

#### For macOS:
Den enkleste måten å installere node.js og npm på Mac er ved bruk av Homebrew. 
For å installere Homebrew:
    Åpne terminalen og skriv: 
```
    ruby-e"$(curl-fsSLhttps://raw.githubusercontent.com/Homebrew/install/master/install)
```
 
For å installere node.js og npm, åpne terminalen og skriv: 
```
    brew update
    brew install node
 ```
#### For  Linux:
Åpne en terminal og skriv:
```
 sudo apt install nodejs
 sudo apt install npm
 Y
 ```
 
### Installasjon av openjdk  12
Gå inn på nettsiden “https://jdk.java.net/archive/”, finn og velg versjon 12.0.2  og ditt operativsystem, i dette tilfelle trykk på “zip” som hører til Windows, så lagre. 

Men denne laster ned kan du åpne filutforskeren og lage en mappe som heter “Java” i den lokale disken som ofte heter C: . 

Gå tilbake til nettleseren og velg “open folder”. Trykk på den nye komprimerte mappen og velg pakk ut, så pakk ut alt og velg så den nylige opprettede mappen Java. 
#### For Windows:
Gå så inn på Control Panel -> System and Security -> System -> System protection -> Advanced -> Environment Variables. 
Under System variables trykk på New. Sett inn “JAVA_HOME” som Variable name og “C:\Java\jdk-12.0.2” som Variable value. Trykk så på ok
Så under System variables trykk på Path så Edit->New  og lim inn følgende “C:\Java\jdk-12.0.2\bin” og trykker OK til slutt. 


#### For Linux
Åpne en terminal og skriv inn:
```
- sudo apt update
- sudo apt install default-jdk
    -Y
```
Sette JAVA_HOME Environment Variable
Skriv følgende inn i terminalen 
```
- sudo update-alternatives --config java
 ```

Kopier stien, i dette tilfelle: `/usr/lib/jvm/java-11-openjdk-amd64/bin/java`.
Skriv inn følgende i en terminlen:
```
sudo nano /etc/environment
JAVA_HOME="usr/lib/jvm/java-11-openjdk-amd64/bin/java"
```
Trykk Ctrl og S for å lagre, så kan du lukke terminalen. 
Logg så inn og ut av brukeren din og openJDK er installert. 
Test så om det fungerte ved å skrive inn:
```
Java -version
``` 
 
#### For macOS:
Åpne terminalen og skriv
```
    brew tap AdoptOpenJDK/openjdk
    brew cask install adoptopenjdk12
```

 
 
### Installasjon av Gradle
Gå inn på denne [nettsiden](https://gradle.org/releases/) og trykk på “binary-only” til den nyeste versjonen, og last ned denne. 
 
Lag på en ny mappe på C: som heter `Gradle`. Pakk så ut den komprimerte mappen du har lastet ned inn i denne nye opprettende mappen kalt `Gradle`. 

#### For Windows
Gå så inn på Control Panel -> System and Security -> System -> System protection -> Advanced -> Environment Variables. Under System variables trykk på Path så Edit->New  og lim inn følgende “C:\Gradle\gradle-6.3\bin” og trykker OK til slutt. 

#### For Linux
Åpne en terminal og skriv inn:
```
- export PATH=$PATH:~/Gradle/gradle-6.3/bin
```
Test så om det fungerte ved å skrive inn:
```
 gradle -v
```


#### For mac
Åpne terminalen, skriv
```
    brew install gradle
```
Test så om det fungerte ved å skrive inn:
```
    gradle -v
```
 
### Installasjon av Cisco AnyConnect Secure Mobility Client
Henviser til til [manual](“https://innsida.ntnu.no/wiki/-/wiki/Norsk/Installere+VPN”) NTNU selv har lagt ut. Gå inn på siden og følg instruksene. Hvis linken sender seg til en nettside uten innhold kan du Google “VPN NTNU” og finne manualen selv på NTNU sine sider. Hvis du benytter en egen database som ikke tilhører NTNU er ikke dette nødvendig. 

### Installasjon av Git
#### For Windows:
Gå inn på [nettsiden](https://gitforwindows.org/) og trykk på Download midt på siden. Når installasjonen starter kan man trykke på Next helt til Finish. 


#### For Linux
Åpne en terminal og skriv in:
```
 sudo apt update
 sudo apt-get install git
  Y
``` 

` git --version` Sjekker om installasjonen var vellykket. 

   

#### For macOS
Dette kan gjøres ved Homebrew. Åpne terminalen og skriv
```
    brew install git
```
    Verifiser at innstallasjonen er vellykket ved å skrive 
```
    git --version
```

## Mappestruktur 
Her er det en illustrasjon av mappestrukturen til produktet. Det er kun de viktigste mappene og filene som er inkludert. 
![Webp.net-resizeimage__2_](/uploads/de675cabb5f9eb4735a335eb46f0bb4c/Webp.net-resizeimage__2_.png)

 
## Hvordan kjøre produktet
For å kjøre produktet trengs det seks programmene, disse er;
- Npm  eller yarn
- Node.js
- Java 11, 12 / OpenJDK 11, 12
- Gradle
- Cisco AnyConnect Secure Mobility Client  (Tilgang til nettet til NTNU)
- Git (for Windows)

Hvis du ikke har disse gå til forrige punkt som heter installasjon av nødvendig  programvare 
for å installere de programmene du ikke har.
### Adgangskontroll
For å få tilgang til koden må denne gis ut, ta kontakt med enten Simen Aabol eller Erik Skår på mail,
for å få ordnet dette. 
- Simen Aabol: simentaa@stud.ntnu.no 
- Erik Skår: erikmsk@stud.ntnu.no. 


### Tilkoble seg nettet til NTNU
Henviser til til [manualen](https://innsida.ntnu.no/wiki/-/wiki/Norsk/Installere+VPN) NTNU selv har lagt ut 
for å tilkople seg nettet til NTNU. Dette må gjøres for å få tilgang til databasen. Hvis du benytter en 
egen database som ikke tilhører NTNU er ikke dette nødvendig. 
Ta kontakt med en av personene under for teknisk hjelp eller hvis du ikke får tilgang til NTNU sitt nett. 
- Simen Aabol: simentaa@stud.ntnu.no 
- Erik Skår: erikmsk@stud.ntnu.no. 

 
#### Åpne riktige mapper
Start med å lage en mappe der du vil at produktet skal ligge. I manualer lager vi en mappe i `C`: som vi
kaller Prosjekt. Åpne så  Git CMD på Windows/ terminal på Mac og naviger deg til mappen du vil legge 
til produktet. 
 

Skriver inn `cd ..` to ganger for å navigere seg bakover i filstrukturen til man kommer til `C`:,  
skriv så `cd Prosjekt` som gjør at man går inn i mappen vi tidligere lagde. 
Skriv så `git clone https://gitlab.stud.idi.ntnu.no/tdt4140-2020/46.git `, 
da vil man få opp en boks der man må skrive inn brukernavn og passord til Gitlab-kontoen sin. 
Får kontoer som NTNU har laget så skal man kun benytte seg av brukernavnet, og ikke hele mailen. 
Skriv så inn`cd 46`, så `cd App` etterfulgt av `gradle run` (hvis denne ikke gjør noe prøv `gradle build` først). 
Det kan fort ta et minutt før “gradle run”-kommandoen er ferdig. Den skal stoppe på 87%, se bilde under. 
Etter den har kommet til 87% får du opp en fane som fra brannmuren, denne kan du godkjenne, 
ved å trykke på Allow Access (kun på Windows). 
 
 
 
Åpne så enda en Git CMD/Terminal på Mac. Her skal det skrives inn `cd ..` -> `cd ..` -> `cd prosjekt` 
-> `cd 46` -> `cd 46` -> `npm install` -> `npm start`. 
Kommandoen `npm install` bør kun kjøres første gang, men det er ikke farlig hvis man kjører den hver gang. 
Windows: får så opp en advarsel fra brannmuren, denne kan godtas. 

Produktet vil så starte opp i nettleseren som er satt som standard. 
NB: Hvis du har Internet Explorer eller den gamle versjonen av Microsoft Edge så må du klippe ut
URL-en og lime den inn i en annen nettleser, eventuelt oppdatere til en nyere versjon av Microsoft Edge. 
 
### Tests
#### Frontend
Frontend har ingen unit-tester, men har heller brukt brukertesting for å sjekke at alt ser ut og oppfører seg som det skal.

#### Backend
For å kjøre testene i backend må man først være tilkoblet NTNU sitt nett. Ved å gå inn i mappen App så kan man kjøre kommandoen “gradle test” som vil starte testene for backenden. Disse testene blir også kjørt av CI på GitLab, så det er viktig at man ikke endrer koden slik at de slutter å fungere uten å oppdatere testene.
## Kreditering 
Vi vil takke vår produkteier Magdalena Uyen Bao Tran for all veiledning gjennom prosjektet. 
https://www.goodreads.com/ har blitt brukt som inspirasjon til den grafiske oppbyggingen av nettsiden og hvilke funksjoner vi burde ha med. 
Gjennomføringen og oppbyggingen  av dette README-dokumentet er utformet ved hjelp av  https://medium.com/@meakaakka/a-beginners-guide-to-writing-a-kickass-readme-7ac01da88ab3, og vil takke dem for all hjelp til å skrive en bra README. 

## Lisens
Copyright ©   2020 Gruppe 46 TDT4140 Programvareutvikling (2020 VÅR)
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.



