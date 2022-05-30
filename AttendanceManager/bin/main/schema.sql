CREATE TABLE IF NOT EXISTS timestamps (
   id INT NOT NULL AUTO_INCREMENT,
   user_id VARCHAR(100) NOT NULL,
   name VARCHAR(100) NOT NULL,
   attendance_time VARCHAR(100) NOT NULL,
   leave_time VARCHAR(100),
   attendance_date date NOT NULL,
   PRIMARY KEY(id)
) ;

CREATE TABLE IF NOT EXISTS employees (
   code VARCHAR(10) NOT NULL,
   name VARCHAR(50) NOT NULL,
   role INT(1) NOT NULL,
   password VARCHAR(20) NOT NULL,
   PRIMARY KEY(code)
) ;