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



Create Role postgres with login;
Grant all on location to postgres;
Grant all on sensor to postgres;
Grant all on temperature to postgres;
Grant all on temperature_temp_id_seq to postgres;
ALTER USER postgres WITH PASSWORD 'suk';
GRANT ALL ON location_location_id_seq to postgres;
--SELECT degrees_c, time_stamp FROM temperature WHERE time_stamp BETWEEN '2014-09-04 10:00:00.0' AND '2014-09-05 00:00:00.0';--
--SELECT AVG(degrees_c) FROM temperature;--
--INSERT INTO temperature (temp_id,location_id) VALUES (1,1);
/*INSERT INTO location (location_id,full_name,abbreviation) VALUES (1,'england','eng');
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-09-04 10:00:00.0',1,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-04 10:00:00.0',100,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2012-05-04 10:00:00.0',1,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-02 10:00:00.0',100,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-05-04 10:00:00.0',50,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-08-04 10:00:00.2',50,'32',1);
INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id) VALUES ('2014-02-04 10:00:00.1',1,'32',1);
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

