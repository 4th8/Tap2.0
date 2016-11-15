DROP DATABASE IF EXISTS tap;
CREATE DATABASE tap;
\c tap


CREATE TABLE location (
location_id serial,
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
location_id int references location(location_id) ,
PRIMARY KEY (time_stamp, sensor_serial)
);


CREATE TABLE defaultSave(
kind text,
filepath text,
PRIMARY KEY (kind)
);
INSERT INTO defaultSave (kind, filepath) VALUES ('import','');
INSERT INTO defaultSave (kind, filepath) VALUES ('export','');


Create Role postgres with login;
Grant all on location to postgres;
Grant all on sensor to postgres;
Grant all on temperature to postgres;
Grant all on temperature_temp_id_seq to postgres;
ALTER USER postgres WITH PASSWORD 'suk';
GRANT ALL ON location_location_id_seq to postgres;
GRANT ALL ON defaultSave to postgres;
--SELECT degrees_c, time_stamp FROM temperature WHERE time_stamp BETWEEN '2014-09-04 10:00:00.0' AND '2014-09-05 00:00:00.0';--
--SELECT AVG(degrees_c) FROM temperature;--
--INSERT INTO temperature (temp_id,location_id) VALUES (1,1);

/*INSERT INTO location (location_id,full_name,abbreviation) VALUES (1,'england','eng');
INSERT INTO sensor (sensor_serial,abbreviation) VALUES ('32342342342','eng');
INSERT INTO sensor (sensor_serial,abbreviation) VALUES ('1111111111','eng');
INSERT INTO sensor (sensor_serial,abbreviation) VALUES ('2222222222','eng');
NSERT INTO temperature (time_stamp,degrees_c,abreviation) VALUES ('2014-05-04 10:00:00.0',1232300,'32342342342',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2012-05-04 10:00:00.0',1233423,'35346457662',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-02 10:00:00.0',1023320,'34563463772',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-04 10:00:00.0',5023243,'375675675762',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-08-04 10:00:00.2',5032323,'356765767572',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2012-05-04 10:00:00.0',12223433,'33453453552',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-02 10:00:00.0',10434340,'36564333332',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-04 10:00:00.0',50344344,'36689999992',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-08-04 10:00:00.2',54334340,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-02-04 10:00:00.1',13434443,'32',1);
*/

--WITH temperature(value) AS (SELECT degrees_c from temperature)SELECT avg(value) AS "Average",min(value),max(value),stddev(value),percentile_cont(0.25) WITHIN GROUP (ORDER BY value) AS "Q1",percentile_cont(0.50) WITHIN GROUP (ORDER BY value) AS "Q2",percentile_cont(0.75) WITHIN GROUP (ORDER BY value) AS "Q3" FROM temperature;
--

WITH temperature(value) AS (
  SELECT degrees_c from temperature
  
)
SELECT
  avg(value) AS "Average",
  min(value),
  max(value),
  stddev(value),
  percentile_cont(0.25) WITHIN GROUP (ORDER BY value) AS "Q1",
  percentile_cont(0.50) WITHIN GROUP (ORDER BY value) AS "Q2",
  percentile_cont(0.75) WITHIN GROUP (ORDER BY value) AS "Q3"
FROM
  temperature;

