
CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY,
                                     fullName VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'CLIENT'
    )

CREATE TABLE IF NOT EXISTS rooms (
                       id UUID PRIMARY KEY,
                       room_number VARCHAR(20) NOT NULL UNIQUE,
                       type VARCHAR(30) NOT NULL,
                       capacity INTEGER NOT NULL,
                       price NUMERIC(10, 2) NOT NULL,
                       status VARCHAR(30) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS reservation_code_seq
START WITH 1
INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS reservations (
                              id UUID PRIMARY KEY,
                              reservation_code VARCHAR(30) NOT NULL UNIQUE,

                              user_id UUID NOT NULL REFERENCES users(id),
                              room_id UUID NOT NULL REFERENCES rooms(id),

                              check_in DATE NOT NULL,
                              check_out DATE NOT NULL,

                              number_of_guests INTEGER NOT NULL,
                              number_of_nights INTEGER NOT NULL,

                              total_price NUMERIC(10, 2) NOT NULL,
                              status VARCHAR(30) NOT NULL,

                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CHECK (check_out > check_in),
                              CHECK (number_of_guests > 0),
                              CHECK (number_of_nights > 0),
                              CHECK (total_price >= 0)
);

CREATE SEQUENCE invoice_number_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE invoices (
                          id UUID PRIMARY KEY,
                          invoice_number VARCHAR(30) NOT NULL UNIQUE,
                          payment_id UUID NOT NULL REFERENCES payments(id),

                          total_ht NUMERIC(10, 2) NOT NULL,
                          vat_rate NUMERIC(5, 2) NOT NULL,
                          vat_amount NUMERIC(10, 2) NOT NULL,
                          total_ttc NUMERIC(10, 2) NOT NULL,

                          issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
                          id UUID PRIMARY KEY,
                          reservation_id UUID NOT NULL UNIQUE,
                          amount NUMERIC(10, 2) NOT NULL,
                          method VARCHAR(30) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                          CONSTRAINT fk_payment_reservation
                              FOREIGN KEY (reservation_id)
                                  REFERENCES reservations(id),

                          CONSTRAINT chk_payment_amount
                              CHECK (amount >= 0)

);