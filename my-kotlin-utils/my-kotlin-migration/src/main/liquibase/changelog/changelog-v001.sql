CREATE TYPE "op_types_type" AS ENUM ('paid', 'unpaid');
CREATE TYPE "op_visibilities_type" AS ENUM ('public', 'admin', 'registered');

CREATE TABLE "ops" (
	"id" text primary key constraint ops_id_length_ctr check (length("id") < 64),
	"order_num" text not null constraint ops_number_length_ctr check (length(order_num) < 20)
	"title" text constraint ops_title_length_ctr check (length(title) < 2000),
	"owner_id" text not null constraint ops_owner_id_length_ctr check (length(id) < 64),
	"amount" numeric not null constraint ops_amount_ctr check (amount >= 0),
	"payment_id" text,
	"payment" numeric,
	"op_type" op_types_type not null,
	"visibility" op_visibilities_type not null,
	"lock" text not null constraint ops_lock_length_ctr check (length(id) < 64)
);

CREATE INDEX ops_op_type_idx on "ops" using hash ("op_type");

CREATE INDEX ops_ordernum_idx on "ops" using hash ("order_num");