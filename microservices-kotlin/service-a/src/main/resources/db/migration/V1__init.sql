create table event (
   id uuid not null,
   created_at timestamp not null,
   updated_at timestamp not null,
   comments varchar(255),
   event_time int8 not null,
   name varchar(255),
   value float4 not null,
   primary key (id)
)