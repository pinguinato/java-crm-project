# Usa un'immagine base ufficiale di Azul Zulu OpenJDK 21 basata su Ubuntu.
FROM azul/zulu-openjdk:21-latest

# Argomento per specificare il nome del file JAR da copiare
ARG JAR_FILE=target/crm-project-0.0.1-SNAPSHOT.jar

# Imposta una directory di lavoro all'interno del container
WORKDIR /opt/app

# Copia il file JAR dalla tua macchina locale (dal percorso specificato in ARG) all'interno del container
COPY ${JAR_FILE} app.jar

# Esponi la porta 8080, quella su cui Spring Boot è in ascolto di default
EXPOSE 8080

# Comando per eseguire l'applicazione quando il container parte
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]