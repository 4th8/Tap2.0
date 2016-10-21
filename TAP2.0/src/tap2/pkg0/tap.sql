DROP DATABASE IF EXISTS tap;
CREATE DATABASE tap;
\c tap


CREATE TABLE location (
location_id text,
full_name text,
abbreviation text,
PRIMARY KEY(location_id)
);

CREATE TABLE sensor (
sensor_serial text,
abbreviation text,
PRIMARY KEY (sensor_serial)
);

CREATE TABLE temperature(
temp_id serial,
time_stamp timestamp,
degrees_c float,
sensor_serial text,
location_id text,
PRIMARY KEY (temp_id)
);

INSERT INTO location (location_id,full_name,abbreviation)VALUES ('123', 'New York', 'ny');
INSERT INTO sensor (sensor_serial, abbreviation) VALUES ('a1', 'ny');
INSERT INTO temperature (temp_id,time_stamp,degrees_c,sensor_serial,location_id) VALUES ('1','1999-01-08 04:05:06',40.0,'a1','123');

Create Role postgres with login;
Grant all on location to postgres;
Grant all on sensor to postgres;
Grant all on temperature to postgres;
Grant all on temperature_temp_id_seq to postgres;
ALTER USER postgres WITH PASSWORD 'suk';
