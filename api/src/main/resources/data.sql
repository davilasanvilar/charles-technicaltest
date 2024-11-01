INSERT INTO
    public.car_types ("id", price_per_day)
VALUES
    ('sedan', 20),
    ('suv', 30),
    ('van', 40) ON CONFLICT
DO NOTHING;

INSERT INTO
    public.users (id, created_at, username)
VALUES
    (
        'f3555698-a1af-443b-88bc-bf2842574736',
        NOW(),
        'admin'
    ) ON CONFLICT
DO NOTHING;

INSERT INTO
    public.cars (id, created_at, "name", type_id)
VALUES
    (
        'cf5eda00-b67b-4eed-8e5d-7f2e4b6f1fee'::uuid,
        '2024-10-31 03:06:22.410',
        'Opel Insignia',
        'sedan'
    ),
    (
        'f3b3b3b4-3b3b-4b3b-8b3b-3b3b3b3b3b3a'::uuid,
        '2024-10-31 03:07:18.099',
        'Peugeot 3008',
        'suv'
    ),
    (
        '7641cc90-eb70-4b65-aef9-e978870bba6e'::uuid,
        '2024-10-31 03:07:18.099',
        'Peugeot 4008',
        'suv'
    ),
    (
        'f3b3b3b4-3b3b-4b3b-8b3b-3b3b3b3b3b3b'::uuid,
        '2024-10-31 03:08:07.123',
        'Mercedes Sprinter',
        'van'
    ),
    (
        'f3b3b3b4-3b3b-4b3b-8b3b-3b3b3b3b3b3c'::uuid,
        '2024-10-31 03:08:07.123',
        'Citroen Jumper',
        'van'
    ) ON CONFLICT
DO NOTHING;