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

-- Droits sur le schéma public pour pouvoir créer des tables
GRANT USAGE, CREATE ON SCHEMA public TO product_manager_user;

--ajout permissions CRUD pour product_manager_user
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO product_manager_user;

GRANT USAGE, SELECT, UPDATE ON SEQUENCE product_id_seq TO product_manager_user;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE product_category_id_seq TO product_manager_user;
