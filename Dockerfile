# Image Java avec Maven et JDK 17
FROM maven:3.9.11-eclipse-temurin-17

# Dossier de travail dans le conteneur
WORKDIR /app

# Copier le fichier Maven
COPY pom.xml .

# Télécharger les dépendances
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler le projet
RUN mvn clean package -DskipTests

# Lancer l'application
CMD ["java", "-cp", "target/classes:target/dependency/*", "Main"]