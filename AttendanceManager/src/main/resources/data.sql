INSERT INTO employees(code, name, role, password)
VALUES ('test_code', 'test_name', 1, '$2a$10$nBCCwX10JR0sIEPxznPksef.Y8a7UtqVNjgCCPuJzoU00yicWTX1y');
INSERT INTO timestamps(id, user_id, name, attendance_time, leave_time, attendance_date)
VALUES (null, 'test_code', 'test_name', '10:00:02.348', '19:00:45.842', '2022-06-01');
INSERT INTO timestamps(id, user_id, name, attendance_time, leave_time, attendance_date)
VALUES (null, 'test_code', 'test_name', '10:00:02.348', '19:00:45.842', '2022-06-02');
INSERT INTO timestamps(id, user_id, name, attendance_time, leave_time, attendance_date)
VALUES (null, 'test_code', 'test_name', '10:00:02.348', '19:00:45.842', '2022-06-03');
INSERT INTO timestamps(id, user_id, name, attendance_time, leave_time, attendance_date)
VALUES (null, 'test_code', 'test_name', '10:00:02.348', '19:00:45.842', '2022-06-06');
INSERT INTO timestamps(id, user_id, name, attendance_time, leave_time, attendance_date)
VALUES (null, 'test_code', 'test_name', '10:00:02.348', null, '2022-06-07');