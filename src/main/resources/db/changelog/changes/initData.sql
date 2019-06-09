insert into gate (name, status)
values ('G01', 'FREE');
insert into gate (name, status)
values ('G02', 'FREE');
insert into gate (name, status)
values ('G03', 'FREE');
insert into gate (name, status)
values ('G04', 'INACTIVE');

insert into location (name, status, zone_number)
values ('A01', 'FREE', 1);
insert into location (name, status, zone_number)
values ('A02', 'FREE', 1);
insert into location (name, status, zone_number)
values ('A03', 'FREE', 1);
insert into location (name, status, zone_number)
values ('A04', 'FREE', 1);
insert into location (name, status, zone_number)
values ('B01', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B02', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B03', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B04', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B05', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B06', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B07', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B08', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B09', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B10', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B11', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B12', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B13', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B14', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B15', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B16', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B17', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B18', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B19', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B20', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B21', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B22', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B23', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B24', 'FREE', 2);
insert into location (name, status, zone_number)
values ('B25', 'FREE', 2);
insert into location (name, status, zone_number, gate_id)
values ('C01', 'FREE', 3, 1);
insert into location (name, status, zone_number, gate_id)
values ('C02', 'FREE', 3, 1);
insert into location (name, status, zone_number, gate_id)
values ('C03', 'FREE', 3, 1);
insert into location (name, status, zone_number, gate_id)
values ('C04', 'FREE', 3, 1);
insert into location (name, status, zone_number, gate_id)
values ('C05', 'FREE', 3, 2);
insert into location (name, status, zone_number, gate_id)
values ('C06', 'FREE', 3, 2);
insert into location (name, status, zone_number, gate_id)
values ('C07', 'FREE', 3, 2);
insert into location (name, status, zone_number, gate_id)
values ('C08', 'FREE', 3, 2);
insert into location (name, status, zone_number, gate_id)
values ('C09', 'FREE', 3, 3);
insert into location (name, status, zone_number, gate_id)
values ('C10', 'FREE', 3, 3);
insert into location (name, status, zone_number, gate_id)
values ('C11', 'FREE', 3, 3);
insert into location (name, status, zone_number, gate_id)
values ('C12', 'FREE', 3, 3);

insert into loader (identity_number, status)
values ('L01', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L02', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L03', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L04', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L05', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L06', 'AVAILABLE');
insert into loader (identity_number, status)
values ('L07', 'INACTIVE');
insert into loader (identity_number, status)
values ('L08', 'INACTIVE');
insert into loader (identity_number, status)
values ('L09', 'BROKEN');
insert into loader (identity_number, status)
values ('L10', 'BROKEN');



insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE01', 'big', 'FREE', 40);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE02', 'big', 'FREE', 40);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE03', 'big', 'FREE', 40);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE04', 'middle', 'FREE', 30);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE05', 'middle', 'FREE', 30);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE06', 'middle', 'FREE', 30);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE07', 'small', 'BROKEN', 21);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE08', 'small', 'BROKEN', 21);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE09', 'small', 'INACTIVE', 21);
insert into vehicle (registration_number, model, status, pallets_capacity)
values ('VE10', 'small', 'INACTIVE', 21);
