# CGI suvepraktikale kandideerimise ülesanne
See hoidla sisaldab kinokülastajale filmi ning saalis istekohtade soovitamise veebirakenduse lähtekoodi. Esimese asjana peab kinokülastaja ennast uueks kasutajaks registreerima või sisse logima Kinokülastajale näidatakse alguses tervet kinokava.
Seejärel saab kinokülastaja valida erinevate filtrite vahel (žanr, vanus, keel ja järgneva nädala päevad) ning vastavalt valikule näeb kasutaja kõiki filme, mis tema kriteeriumitele vastavad. Juhul kui kasutaja on juba mõnda filmi vaadanud, saab
kasutaja vajutada nuppu "Soovita filme vaatamisajaloo põhjal", mille tagajärjel näeb kasutaja filme mida soovitusalgoritm talle soovitab. (loogika asub https://github.com/laurileppik/MovieRecommender/blob/master/src/main/java/com/cgi/lauri/movieRecommender/logic/MovieRecommenderLogic.java)
Sellelt lehelt on võimalik kasutajal vajutada nuppu "Vaata aegu", mille vajutamisel näitab algoritm aegu filmile, mille peale vajutati (juhul kui filmile vaatamisaegu eksisteerib). Avaekraanile tagasi jõudes on meil võimalik vajutada nuppu 
"Osta piletid", mille tulemusel jõuame lehele, kus saame valida piletite arvu ning seejärel nupp "Vali kohad" vajutades jõuame uuele lehele. Sellel lehel on kinosaali istumisplaan, kus punasega näidatakse kinnised, rohelisega vabu ning kollasega
kasutajale soovitatavaid kohti. (vastav loogika asub https://github.com/laurileppik/MovieRecommender/blob/master/src/main/java/com/cgi/lauri/movieRecommender/logic/ScreenLogic.java ) Kinnised kohad genereeritakse igal lehele suunamisel juhuslikult. Siit saab kasutaja valida, kas "Kinnita", mille vajutamisel küsitakse kasutajalt hinnangut filmi kohta või tühista, mille vajutamisel minnakse tagasi
avalehele. Lisaks on olemas veel 2 aadressi /admin/addmovie ja /admin/showtime, kus saab vastavalt filme ja vaatamisaegu lisada.

Suur osa sisse logimise ja registreerumisega seotud loogikast on saadud siit: [1] https://www.geeksforgeeks.org/spring-security-login-page-with-react/

Suur osa selle projeti struktuurist ja loogikast on saadud siit: [2] https://www.youtube.com/watch?v=dP4goCkKxlw&list=PLGRDMO4rOGcODJeYSY08lIILkqoydQI2k&ab_channel=JavaGuides

Nendest linkidest võetud koodi on otse kopeeritud ja muudetud vastavalt selle ülesande lahendamiseks sobival viisil.

# Projekti käivitamine ja kasutamine
1) Lae alla XAMPP (https://www.apachefriends.org/), lae alla IntellJ (https://www.jetbrains.com/idea/), lae alla VSC (https://code.visualstudio.com/). 
2) Ava XAMPP Control Panel. Vajuta Apache käivitamiseks "Start", vajuta "MYSQL" käivitamiseks "Start" ja vajuta andmebaasi avamiseks "Admin". Juhul kui MYSQL ei jookse pordil 3306, vajuta Config -> Service and Port Settings -> MYSQL
ja vaheta seal port 3306ks.
3) Tee uus tabel nimega "movierecommender". 
4) Vajuta tabeli peale ning vajuta Impordi. Lae alla andmebaasi fail (vajalik kui ei taha ise andmeid sisestada) ning vajuta "Impordi"
5) Tee endale projekti jaoks folder, ava Terminal ning kirjuta "git clone https://github.com/laurileppik/MovieRecommender.git"
6) Ava projekti folder Intelljga ning jooksuta klassi MovieRecommenderApplication. Vajuta "Enable annotation processing" kui Lomboki hoiatus ette tuleb.
7) Ava VSC foldrist MovieRecommender -> mrs-frontend. Kirjuta terminali npm install, npm run dev.
8) Kirjuta brauserisse http://localhost:3000/
9) Logi sisse nt kasutajaga kasutajanimi: a parool: a

# Lahenduse kirjutamise protsess
Alustasin projekti kirjutamisel ülesandes nõutud vastavate tehnoloogiatega tutvumisest. Java ning JPAga olin juba tuttav, kuid Spring Booti ja Reactiga pidin tutvust tegema. Seejärel tekitasin RESTi loogika kirjutamiseks erinevad klassid vastavalt
nii front- kui ka backendis videoseeria [2] abiga. Pärast seda tekitasin erinevate CRUD operatsioonide jaoks loogika ning lõin andmebaasi andmete säilitamiseks. Samal ajal alustasin ka Reactiga front-endi kirjutamist, kuhu lisasin Bootstrapi abiga
tabelid erinevate filmide ja seansside info hoidimiseks. Peale seda lisasin võimaluse žanrite järgi sorteerida ning alustasin tööd saali komponendiga. Seejärel alustasin istekohtade soovitamisalgoritmi välja töötamist. Peale mida lisasin uue tabeli MovieRating
kus hoiustan isikuga seotud vaadatud filmiinformatsiooni. (vaadatud film + reiting) Seejärel tekitasin [1] abiga endale registreerimise ja sisse/välja logimise. Peale seda alustasin tööd veel teiste filtrite kallal, lisasin algoritmi filmide soovitamiseks
ning lisasin OMDb APIst saadud informatsiooni filmide lisamisele. Viimaks tegelesin stylemise ja refactorimisega.

# Olulised klassid/Struktuur
### Backend
Ülesande lahendamiseks jagasin backendi erinevateks packageiteks, mille sees olevad klassid vastavad projekti erinevate osade loogika eest. 

contoller klassid - Tegelevad sisse tulevate HTTP requestidega ning tagastavad vastava vastuse

&emsp; UserController - Ainult registreerimis/sisse/väljalogimis operatsioonid
  
&emsp; CustomerController - Kõik muud kasutajaga seotud operatsioonid

dto klassid - controller klasside ja service klasside vahepealne kiht, kus front-endiga suhtlemiseks vajalikud väljad teisendatakse dto objektideks (model klassidega samaväärne klass, tehtud andmete edastamiseks controllerile)

exception klass - Selles projektis vajalik vaid juhul kui andmebaasist vajalikku objekti ei leitud

logic klassid - Vastutavad projektis kirjutatud loogika eest

mapper klassid - Mapivad modeli klassid dtodeks ja vastupidi

repository liidesed - Tegelevad andmebaasile info edastamise ja sealt info saamisega

security klassid - Tegelevad sisse/välja logimisega, registreerimisega, jwt valideerimise, koostamisega, erinevatele kasutajatele õiguste andmisega

service klassid/liidesed - Nende abil saame defineerida äriloogika
### Frontend
components

&emsp; admin - klassid tegelevad admin protseduuridega nt filmide lisamine, seansside lisamine

&emsp; auth - autentimisega seotud klassid

&emsp; kõik muud komponendid, mida kasutame App.jsx klassis veebilehe renderdamisel

css klassid - stiilimiseks vajalikud klassid

services klassid - klassid, mis kasutavad axiost, et backendile HTTP requeste saata

App.jsx - peaklass, kus on kõik routeimise loogika

# Probleemid, lahendused ja kulunud aeg
Kui 3 päeva välja arvata, siis kulutasin 2 nädala jooksul iga päev sellele projektile keskmiselt 8-9 tundi päevas (kokku ~90h).

Kõige keerulisem oli selle töö juures kindlasti välja mõelda kuidas SpringBooti + Reactiga autentimine adekvaatselt tööle saada. Vaatasin läbi mitu videot, õpetust internetis ning konsulteerisin ka ChatGPTga, kuid millestki polnud pikalt abi.
Viimaks leidsin juhendi [1], mille abil sai autentimise piisavalt adekvaatselt tööle.

Lisaks sellele oli esialgu keeruline andmebaasi struktuur välja mõelda, sest alguses ei tea alati päris 100% täpselt, missuguseid tabeleid päriselt vaja läheb ning kuidas oleks mõistlik viis andmebaasi struktureerida. Kuid selle probleemi sai
hõlpsasti erinevate veebilehtede (nt Baeldung oli väga kasulik) materjalidega lahendatud.

Väiksemaid probleeme sai enamasti lahendatud koodi hoolikalt ning uuesti lugedes ning tavalisi Debuggimise meetodeid kasutades. Ühtlasi oli loomulikult palju abi ka internetist ja tehisintellektist.

Kuna lõpus jäi aega väheseks, ei saanud päris kõike implementeerida, mida oleksin soovinud. Kindlasti tahaksin, et veebileht näeks efektsem välja, oleksin tahtnud juurde lisada veel võimaluse mingi arv seansse automaatselt iga päev lisada, et 
neid testimiseks kogu aeg manuaalselt ei peaks lisama, oleksin tahtnud juurde lisada testid, näidata ainult tulevasi seansse ning, andmebaasi lisamisel constrainte tekitada, sest hetkel saab peaaegu ükskõik missugusele ajale uut seanssi panna (kuid tegelikult samal ajal samas saalis seanssi olla ei saa) ning teatud kohtades projekti loogikat ümber kirjutada. 
