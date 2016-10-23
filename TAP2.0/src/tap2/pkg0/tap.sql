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

INSERT INTO location (location_id,full_name,abbreviation)VALUES ('1', 'New York', 'nyc');
INSERT INTO sensor (sensor_serial, abbreviation) VALUES ('625102', 'nyc');
INSERT INTO temperature (temp_id,time_stamp,degrees_c,sensor_serial,location_id) VALUES ('1','1999-01-08 04:05:06',40.0,'a1','123');

Create Role postgres with login;
Grant all on location to postgres;
Grant all on sensor to postgres;
Grant all on temperature to postgres;
Grant all on temperature_temp_id_seq to postgres;
ALTER USER postgres WITH PASSWORD 'suk';
--SELECT degrees_c, time_stamp FROM temperature WHERE time_stamp BETWEEN '2014-09-04 10:00:00.0' AND '2014-09-05 00:00:00.0';--
--SELECT AVG(degrees_c) FROM temperature;--