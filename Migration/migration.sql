-- Shifts Import --
insert into OEE_DATA_STORE.shift(old_id, name, start_time, stop_time, area, spans_to_next_day, status, created_on,
                                 created_by)
select so.id             as old_id,
       so.name           as name,
       so.start_time     as start_time,
       so.stop_time      as stop_time,
       a.ni              as area,
       false             as spans_to_next_day,
       true              as status,
       CURRENT_TIMESTAMP as created_on,
       'SYSTEM'          as created_by
from BD_OLD.shift so
         join (select ao.id as oi, an.id as ni
               from BD_OLD.area ao
                        join OEE_DATA_STORE.area an
               where ao.name = an.name) as a on so.area = a.oi;

-- Machines Import --

insert into OEE_DATA_STORE.machine(id,
                                   name,
                                   production_limit,
                                   speed,
                                   status,
                                   lsa_target,
                                   stretched_target,
                                   tolerance,
                                   version,
                                   area,
                                   created_on,
                                   created_by)
select machine_id        as id,
       name,
       production_limit,
       speed,
       status,
       lsa_target,
       stretched_target,
       tolerance,
       version,
       ar.ni             as area,
       CURRENT_TIMESTAMP as created_on,
       'SYSTEM'          as created_by
from BD_OLD.machine mo
         join (select ao.id as oi, an.id as ni
               from BD_OLD.area ao
                        join OEE_DATA_STORE.area an
               where ao.name = an.name) ar on mo.area = ar.oi;


insert into OEE_DATA_STORE.station(id, name, status, machine, version, created_on, created_by)
select station_id        as id,
       name,
       status,
       ma.ni             as machine,
       0                 as version,
       CURRENT_TIMESTAMP as created_on,
       'SYSTEM'          as created_by
from BD_OLD.station so
         join (select mo.id oi, mn.id ni
               from BD_OLD.machine mo
                        join OEE_DATA_STORE.machine mn on mo.machine_id = mn.id) ma on so.machine = ma.oi;

-- Users Import --

insert into OEE_DATA_STORE.users(assigned,
                                 email,
                                 first_name,
                                 gid,
                                 last_name,
                                 role,
                                 status,
                                 version,
                                 created_by,
                                 created_on)
select assigned,
       email,
       firstname,
       gid,
       lastname,
       role,
       status,
       version,
       'SYSTEM'          as created_by,
       CURRENT_TIMESTAMP as created_on
from BD_OLD.user;

-- Machine Operator Connection Import --

insert into OEE_DATA_STORE.operators(machine, users)
select mob.machine as machine, un.id as users
from (select mo.machine_id as machine, uo.email as operator
      from BD_OLD.machine_operators moo
               join BD_OLD.machine mo on moo.machine = mo.id
               join BD_OLD.user uo on moo.operator = uo.id) mob
         join OEE_DATA_STORE.users un on un.email = mob.operator;


-- Formula & Template Import --

insert into OEE_DATA_STORE.formula(old_id, status,
                                   created_on,
                                   created_by,
                                   version,
                                   operand,
                                   type,
                                   operation,
                                   area,
                                   in_kg)
select fo.id             as old_id,
       true              as status,
       CURRENT_TIMESTAMP as created_on,
       'SYSTEM'          as created_by,
       0                 as version,
       operand,
       type,
       operation,
       ar.ni             as area,
       in_kg
from BD_OLD.formula fo
         join (select ao.id as oi, an.id as ni
               from BD_OLD.area ao
                        join OEE_DATA_STORE.area an
               where ao.name = an.name) ar on fo.area = ar.oi;

insert into OEE_DATA_STORE.template(description, sign, old_id, type, area, formula, status, version, created_on,
                                    created_by)
select teo.description   as description,
       teo.sign          as sign,
       teo.id            as old_id,
       teo.type          as type,
       ab.ni             as area,
       null              as formula,
       true              as status,
       0                 as version,
       CURRENT_TIMESTAMP as created_on,
       'SYSTEM'          as created_by
from BD_OLD.template teo
         join (select ao.id as oi, an.id as ni
               from BD_OLD.area ao
                        join OEE_DATA_STORE.area an on ao.name = an.name) ab on teo.area = ab.oi;


select ten.id, ten.description, ten.type, fn.id from OEE_DATA_STORE.template ten
    join BD_OLD.template teo on ten.old_id=teo.id
    join OEE_DATA_STORE.formula fn on teo.formula=fn.old_id;

update OEE_DATA_STORE.template ten
    join BD_OLD.template teo on ten.old_id=teo.id
    join OEE_DATA_STORE.formula fn on teo.formula=fn.old_id
set ten.formula=fn.id where ten.old_id=teo.id and fn.old_id=teo.formula;
-- Product Import --

insert into OEE_DATA_STORE.product(catalog_num,
                                   description,
                                   type,
                                   weight,
                                   area,
                                   status,
                                   version,
                                   created_by,
                                   created_on)
select catalog_number,
       description,
       type,
       weight,
       ar.ni             as area,
       true              as status,
       0                 as version,
       'SYSTEM'          as created_by,
       CURRENT_TIMESTAMP as created_on
from BD_OLD.product po
         join (select ao.id as oi, an.id as ni
               from BD_OLD.area ao
                        join OEE_DATA_STORE.area an
               where ao.name = an.name) ar on po.area = ar.oi;


-- Reason Import --

insert into OEE_DATA_STORE.reason(old_id, reason, status, type, version, created_on, created_by)
select id, reason, status, type, 0 as version, CURRENT_TIMESTAMP as current_on, 'SYSTEM' as created_by
from BD_OLD.reason;

update OEE_DATA_STORE.reason
set type = 'COMMON'
where type = 'PLANNED';


insert into station_specific_reasons(machine,
                                     reason,
                                     station,
                                     status,
                                     created_by,
                                     created_on,
                                     version)
select m.machine_id,
       rn.id,
       s.station_id,
       true,
       'SYSTEM',
       CURRENT_TIMESTAMP,
       0
from BD_OLD.reason_association rao
         join BD_OLD.machine m on m.id = rao.machine
         join BD_OLD.station s on rao.station = s.id
         join OEE_DATA_STORE.reason rn on rao.reason = rn.old_id;


-- Entry Import --
insert into OEE_DATA_STORE.entry(id,
                                 status,
                                 availability,
                                 shift_date,
                                 expected_machine_speed,
                                 oee,
                                 performance,
                                 quality,
                                 remarks,
                                 created_on,
                                 total_pdt,
                                 total_production,
                                 total_rejection,
                                 good_production,
                                 total_udt,
                                 version,
                                 machine,
                                 product,
                                 shift,
                                 operator)
select eo.entry_id,
       true as status,
       eo.availability,
       eo.shift_date,
       eo.expected_machine_speed,
       eo.oee,
       eo.performance,
       eo.quality,
       eo.remarks,
       eo.timestamp,
       eo.totalpdt,
       eo.total_production,
       eo.total_rejection,
       eo.good_production,
       eo.totaludt,
       eo.version,
       mo.machine_id,
       pb.p_ni,
       sn.id,
       ub.u_ni
from BD_OLD.entry eo
         join OEE_DATA_STORE.shift sn on eo.shift = sn.old_id
         join BD_OLD.machine mo on eo.machine = mo.id
         join (select uo.id as u_oi, un.id as u_ni
               from BD_OLD.user uo
                        join OEE_DATA_STORE.users un on uo.email = un.email) ub on eo.operator = ub.u_oi
         join (select po.id as p_oi, pn.id as p_ni
               from BD_OLD.product po
                        join OEE_DATA_STORE.product pn on po.catalog_number = pn.catalog_num) as pb
              on pb.p_oi = eo.product
where eo.status = 'SUBMITTED';

update OEE_DATA_STORE.entry
set created_by = BIN_TO_UUID(operator);

update OEE_DATA_STORE.entry
set remarks=null
where LENGTH(TRIM(remarks)) < 1;

insert into OEE_DATA_STORE.downtime(id,
                                    associated_with,
                                    custom_reason,
                                    time,
                                    type,
                                    entry,
                                    created_on,
                                    created_by,
                                    status)
select breakdown_id,
       associated_with,
       reason,
       time,
       type,
       eb.nid,
       eb.created_on,
       eb.created_by,
       true
from BD_OLD.breakdown bo
         join (select eo.id as oid, en.id as nid, en.created_on, en.created_by
               from BD_OLD.entry eo
                        join OEE_DATA_STORE.entry en on eo.entry_id = en.id) eb on bo.entry = eb.oid;

update OEE_DATA_STORE.downtime
set type = 'COMMON'
where type = 'PLANNED';

update OEE_DATA_STORE.downtime dn
    inner join OEE_DATA_STORE.reason rn on LOWER(TRIM(dn.custom_reason)) = LOWER(rn.reason)
set dn.reason = rn.id;

update downtime
set custom_reason=null
where reason IS NOT NULL;


select count(*)
from BD_OLD.breakdown
where reason = 'Cleaning and Challenge Test'
   OR reason = 'Cleaning and Challange Test'
   OR reason = 'Challange Test And Cleaning'
   OR reason = 'Challange Teast And Cleaning'
   OR reason = 'Challange And Cleaning'
   OR reason = 'Challenge Test & Machine Cleaning'
   OR reason = 'Challange Test And Clening'
   OR reason = 'Challenge Test &amp; Cleaning'
   OR reason = 'Challange Tesrt And Cleaning'
   OR reason = 'Challange Test Anfd Cleaning';

update downtime
set custom_reason='Cleaning and Challenge Test'
where custom_reason = 'Cleaning and Challenge Test'
   OR custom_reason = 'Cleaning and Challange Test'
   OR custom_reason = 'Challange Test And Cleaning'
   OR custom_reason = 'Challange Teast And Cleaning'
   OR custom_reason = 'Challange And Cleaning'
   OR custom_reason = 'Challenge Test & Machine Cleaning'
   OR custom_reason = 'Challange Test And Clening'
   OR custom_reason = 'Challenge Test &amp; Cleaning'
   OR custom_reason = 'Challange Tesrt And Cleaning'
   OR custom_reason = 'Challange Test Anfd Cleaning';

update OEE_DATA_STORE.downtime
set version=0;

update OEE_DATA_STORE.downtime
set reason_type='CONNECTED'
where reason IS NOT NULL;

update OEE_DATA_STORE.downtime
set reason_type='CUSTOM'
where reason IS NULL;


insert into OEE_DATA_STORE.rejection(description,
                                     sign,
                                     value,
                                     entry,
                                     status,
                                     created_on,
                                     created_by,
                                     version)
select description,
       sign,
       value,
       eb.nid,
       true,
       created_on,
       created_by,
       0
from BD_OLD.rejection po
         join (select eo.id as oid, en.id as nid, en.created_on, en.created_by
               from BD_OLD.entry eo
                        join OEE_DATA_STORE.entry en on eo.entry_id = en.id) eb on eb.oid = po.entry;
