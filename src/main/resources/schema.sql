
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