SELECT 
    f.id_factura,
    f.fecha,
    f.total,
    p.nombre AS nombre_paciente,
    u.nombre AS nombre_usuario,
    s.nombre AS nombre_servicio,
    df.cantidad,
    df.subtotal
FROM factura f
INNER JOIN paciente p ON f.id_paciente = p.id_paciente
INNER JOIN usuario u ON f.id_usuario = u.id_usuario
INNER JOIN detalle_factura df ON f.id_factura = df.id_factura
INNER JOIN servicio s ON df.id_servicio = s.id_servicio
WHERE f.id_factura = 12;
