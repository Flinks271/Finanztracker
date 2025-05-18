# Finanztracker

Finanztracker ist eine Anwendung zur Verwaltung und Nachverfolgung persönlicher Finanzen. Sie bietet Funktionen zur Verwaltung von Konten, Transaktionen und wiederkehrenden Buchungen.

---

## Voraussetzungen

- **Java**: Stellen Sie sicher, dass Java installiert ist.
- **Maven**: Für den Build- und Testprozess wird Maven empfohlen.
- **Docker**: Für die einfache Bereitstellung einer PostgreSQL-Datenbank wird Docker benötigt.

### Zeichencodierung

Um eine korrekte Darstellung von Sonderzeichen im Terminal zu gewährleisten, setzen Sie die Codepage auf UTF-8:

```bash
chcp 65001
```

---

### Datenbank-Konfiguration

Die Anwendung benötigt eine PostgreSQL-Datenbank. Die Zugangsdaten werden über `.env`-Dateien bereitgestellt.

#### 1. Datenbank mit Docker erstellen

Legen Sie im `docker`-Verzeichnis eine `.env`-Datei mit folgenden Variablen an:

```
POSTGRES_USER=IhrBenutzername
POSTGRES_PASSWORD=IhrPasswort
POSTGRES_DB=IhrDatenbankname
```

Starten Sie anschließend den Datenbank-Container mit Docker Compose.

#### 2. Anwendungskonfiguration

Legen Sie im Projekt-Hauptverzeichnis eine `.env`-Datei mit folgenden Variablen an:

```
POSTGRES_USER=IhrBenutzername
POSTGRES_PASSWORD=IhrPasswort
POSTGRES_DB=IhrDatenbankname
POSTGRES_URL=jdbc:postgresql://localhost:5432/finanztracker
```

> **Hinweis:**  
> Passen Sie ggf. den Port (`5432`) an, falls Sie diesen im Docker-Setup geändert haben.

---

## Build & Start

### Anwendung kompilieren

```bash
mvn clean install
```

### Anwendung starten

Führen Sie die `Main`-Klasse aus:

```
a-finanztracker-plugin/src/main/java/de/dhbw/finanztracker/Main.java
```

Alternativ können Sie die Anwendung mit Maven starten:

```bash
mvn -pl a-finanztracker-plugin exec:java -Dexec.mainClass="de.dhbw.finanztracker.Main"
```

---


## Lizenz

Muss erweiteret werden? bzw muss ichnoch raussuchen doer so.

---

## Mitwirken

Pull Requests sind willkommen! Bitte erstellen Sie ein Issue, bevor Sie größere Änderungen vornehmen.

---

## Kontakt

Bei Fragen oder Problemen wenden Sie sich bitte an das Entwicklerteam.