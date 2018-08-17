DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_obtener_estado`(
	pi_cod_ped VARCHAR(10),
    pi_cod_hoj_rut VARCHAR(10)
    ) RETURNS varchar(20) CHARSET utf8
BEGIN

	DECLARE p_result VARCHAR(20) DEFAULT '';
    
	DECLARE p_pactada  datetime;
    DECLARE p_incumplimiento  datetime;
    DECLARE p_despacho  date;
    DECLARE p_reprogramacion  date;
    DECLARE p_cancelacion  datetime;
    DECLARE p_devolucion  date;
    DECLARE p_tienda_devolucion  varchar(10);
    
    select 
		druta.fec_pact_desp,
        druta.fec_no_cump_desp,
        pedido.fec_desp_ped,
        pedido.fec_repro_ped,
        pedido.fec_canc_ped,
        pedido.fec_devo_ped,
        pedido.cod_tiend_devo
	into
		p_pactada,
        p_incumplimiento,
        p_despacho,
        p_reprogramacion,
        p_cancelacion,
        p_devolucion,
        p_tienda_devolucion
    from tb_detalle_hoja_ruta druta
    inner join tb_pedido pedido 
    on druta.cod_ped = pedido.cod_ped
    where druta.cod_hoj_rut = pi_cod_hoj_rut
    and druta.cod_ped = pi_cod_ped;
    
    IF (p_pactada IS NULL
		AND p_incumplimiento IS NULL
		AND ((p_despacho = CURDATE() AND p_reprogramacion IS NULL AND p_cancelacion IS NULL) OR
         (p_reprogramacion = CURDATE() AND p_cancelacion IS NULL) OR
         (p_cancelacion IS NOT NULL AND p_devolucion = CURDATE() AND p_tienda_devolucion IS NULL))
		) THEN
        SET p_result = 'Pendiente';
        
	ELSEIF (p_pactada IS NOT NULL) THEN
		SET p_result = 'Atendido';
        
	ELSEIF (p_incumplimiento IS NOT NULL) THEN
		SET p_result = 'No Atendido';
        
	ELSEIF (p_reprogramacion > CURDATE()) THEN
		SET p_result = 'Reprogramado';
        
	ELSEIF (DATE(p_cancelacion) = CURDATE()) THEN
		SET p_result = 'Cancelado';
	END IF;

    
RETURN p_result;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_obtener_nombre_destinatario`(
	pi_cod_ped VARCHAR(10)
) RETURNS varchar(200) CHARSET utf8
BEGIN
	-- DESTINATARIO : TIENDA O CLIENTE
    DECLARE p_fecha_retiro DATE;
    DECLARE p_result VARCHAR(100);
    
    SELECT fec_ret_tiend INTO p_fecha_retiro 
    FROM tb_pedido 
    WHERE cod_ped = pi_cod_ped;
    
    IF p_fecha_retiro IS NULL THEN 
    SELECT CONCAT(cli.nom_cli," ",cli.ape_cli) INTO p_result
		FROM tb_pedido pedido INNER JOIN
        tb_cliente cli ON cli.cod_cli = pedido.cod_cli
        WHERE pedido.cod_ped = pi_cod_ped;
	ELSE
    SELECT tienda.nom_tiend INTO p_result
		FROM tb_pedido pedido INNER JOIN
        tb_tienda tienda ON tienda.cod_tiend = pedido.cod_tiend_desp
        WHERE pedido.cod_ped = pi_cod_ped;
    END IF;
RETURN p_result;
END$$
DELIMITER ;
