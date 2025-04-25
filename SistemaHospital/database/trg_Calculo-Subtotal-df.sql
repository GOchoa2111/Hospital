CREATE TRIGGER trg_calcular_subtotal
ON detalle_factura
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE df
    SET df.subtotal = df.cantidad * s.precio
    FROM detalle_factura df
    INNER JOIN inserted i ON df.id_detalle = i.id_detalle
    INNER JOIN servicio s ON i.id_servicio = s.id_servicio;
END;