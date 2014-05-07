kontoapp
========
Demo applikasjon som består av følgende moduler:

- **kontoapp-ejb**, en ejb module, avhenger ikke av andre moduler i prosjektet.
- **kontoapp-war**, web module, avhenger av *kontoapp-ejb* (se pom fila). Merk at vi satt *scope* til å være *provided* noe som medfører at den ikke blir kopiert i war fila.
- **kontoapp-ear**, enterprise module, avhenger av *kontoapp-ejb* og *kontoapp-war* (inkluder begge disse) i ear den endgelige ear filen.
- **kontoapp-client**, en ekstra (optional) client module, der en Swing applikasjon lytter på en JMS destinasjon og meldingene som sendes fra war modulen (se `AccountServlet`) blir levert her.

Hvordan skal man bygge prosjektet?
------
Man må se på avhengighetene. Siden både ear og war module avhenger av ejb, må vi bygge den først. Bruk enten Intellij eller kommandolinje til å kjøre maven. Man må kjøre `mvn clean install`.

Nummer to å bygge vil være war modulen. Siden vi har nå bygget ejb modulen, kan vi nå (på samme måte som vi bygget ejb modulen) bygge war modulen.

Sist skal vi bygge ear modulen. Her skal vi kjøre `mvn package`. Den endelige ear filen skal deployes til Glassfish (enten fra kommandolinje eller fra GUI til administrasjonskonsollet).

Kontoapp-client modulen er ment som demo av JMS.




* * *

Derby
------

Refererer til `DERBY_HOME` som installasjonskatalog, f.eks. hvis glassfish er installert på `c:\glassfish4`, så vil `DERBY_HOME` peke til `c:\glassfish4\javadb`.

Tilkobling til database med `ij`, start `ij`: %DERBY_HOME%\bin\ij

    connect 'jdbc:derby://localhost:1527/sun-appserv-samples;create=true';

Oppretting av tabell:

    create table account (accountNumber int not null, accountName varchar(100), pin int, accountType varchar(50), primary key (accountNumber));


Andre tips:

Se liste over tabeller:

    show tables;

Se datadefinisjonen til tabellen:

    describe account;


Mer tips finner du her:   [http://www.vogella.com/tutorials/ApacheDerby/article.html](http://www.vogella.com/tutorials/ApacheDerby/article.html "Title")
