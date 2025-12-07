--creation db
CREATE DATABASE product_management_db;

--connexion au db
\c product_management_db;

-- Création de l'utilisateur
CREATE USER product_manager_user WITH PASSWORD '123456';

--ajout privièges connection au db
GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;

--ajout privilèges créations table
 GRANT CREATE ON DATABASE product_management_db TO product_manager_user;