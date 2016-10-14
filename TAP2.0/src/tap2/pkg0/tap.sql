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
temp_id text,
time_stamp text,
degrees_c int,
sensor_serial text,
location_id text,
PRIMARY KEY (temp_id)
);

INSERT INTO location (location_id,full_name,abbreviation)VALUES ('123', 'New York', 'ny');
INSERT INTO sensor (sensor_serial, abbreviation) VALUES ('a1', 'ny');
INSERT INTO temperature (temp_id,time_stamp,degrees_c,sensor_serial,location_id) VALUES ('1','12:00:00',40,'a1','123');