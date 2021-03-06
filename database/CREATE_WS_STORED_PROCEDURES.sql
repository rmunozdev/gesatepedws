DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_describir_pedido`(
	pi_codigo_pedido VARCHAR(10),
    pi_codigo_bodega VARCHAR(10)
    )
BEGIN
	select 
	detalle.cod_prod,
    producto.nom_prod,
    detalle.cant_prod
    -- TODO Tipo de producto (DESPACHO - RETIRO)
	from tb_detalle_pedido detalle 
	inner join tb_producto producto
    on producto.cod_prod = detalle.cod_prod
	where detalle.cod_ped = pi_codigo_pedido 
    and detalle.cod_bod = pi_codigo_bodega
    order by producto.cod_prod asc;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_listar_motivos`(
	pi_categoria VARCHAR(4)
    )
BEGIN
	select 
		cod_mot_ped as codigo,
        desc_mot_ped as descripcion,
        cat_mot_ped as categoria
    from tb_motivo_pedido
    where cat_mot_ped = pi_categoria
    ;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_choferes`()
BEGIN
	select 
		num_brev_chof, 
        nom_chof, 
        ape_chof,
        telf_chof,
        email_chof
        
	from tb_chofer 
    where flag_activ_chof = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_cliente_por_email`(
	pi_cod_cli VARCHAR(10),
    pi_email VARCHAR(50)
)
BEGIN

	SELECT 
		cod_cli,
        nom_cli,
		ape_cli,
		num_dni_cli,
		telf_cli,
		email_cli, 
		dir_cli,
		cod_dist
	FROM tb_cliente
    WHERE email_cli = pi_email
    AND cod_cli <> pi_cod_cli;
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_cliente_por_telefono`(
	pi_cod_cli VARCHAR(10),
    pi_telefono VARCHAR(9)
)
BEGIN

	SELECT 
		cod_cli,
        nom_cli,
		ape_cli,
		num_dni_cli,
		telf_cli,
		email_cli, 
		dir_cli,
		cod_dist
	FROM tb_cliente
    WHERE telf_cli = pi_telefono
    AND cod_cli <> pi_cod_cli;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_detalle_ruta`(
	pi_codigo_ruta VARCHAR(10)
)
BEGIN
    select 
		detalle.ord_desp_ped as orden,
		detalle.cod_ped as _pedido,
		fn_obtener_nombre_destinatario(detalle.cod_ped) as destinatario,
		CONCAT(pedido.dir_desp_ped,' ',distrito.nom_dist) as domicilio,
		CONCAT('De ' , ventana.hor_ini_vent_hor,' a ',ventana.hor_fin_vent_hor) as horario,
        ventana.hor_ini_vent_hor,
        ventana.hor_fin_vent_hor,
        fn_obtener_estado(detalle.cod_ped,detalle.cod_hoj_rut) as estado
    from tb_detalle_hoja_ruta detalle
    inner join tb_ventana_horaria ventana on ventana.cod_vent_hor = detalle.cod_vent_hor
    inner join tb_pedido pedido on pedido.cod_ped = detalle.cod_ped
    inner join tb_distrito distrito on distrito.cod_dist = pedido.cod_dist_desp_ped
    WHERE detalle.cod_hoj_rut = pi_codigo_ruta
    order by detalle.ord_desp_ped asc;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_estado_ruta`(
	pi_codigo_hoja_ruta VARCHAR(10),
    OUT po_pendientes INTEGER,
    OUT po_atendidos INTEGER,
    OUT po_no_atendidos INTEGER,
    OUT po_reprogramados INTEGER,
    OUT po_cancelados INTEGER
)
BEGIN
	DECLARE pendientes INTEGER DEFAULT 0;
    DECLARE atendidos INTEGER DEFAULT 0;
    DECLARE no_atendidos INTEGER DEFAULT 0;
    DECLARE reprogramados INTEGER DEFAULT 0;
    DECLARE cancelados INTEGER DEFAULT 0;
    DECLARE estado VARCHAR(50) DEFAULT "";
    
    DEClARE c_estado CURSOR FOR 
	select fn_obtener_estado(cod_ped,cod_hoj_rut)
    from tb_detalle_hoja_ruta 
    where cod_hoj_rut = pi_codigo_hoja_ruta;
    
    OPEN c_estado;
    
    count_estados: LOOP
    FETCH c_estado INTO estado;
    
    IF (estado = 'PENDIENTE') THEN 
		SET pendientes = pendientes + 1;
	ELSEIF (estado = 'ATENDIDO') THEN 
		SET atendidos = atendidos + 1;
	ELSEIF (estado = 'NO ATENDIDO') THEN 
		SET no_atendidos = no_atendidos + 1;
	ELSEIF (estado = 'REPROGRAMADO') THEN 
		SET reprogramados = reprogramados + 1;
	ELSEIF (estado = 'CANCELADO') THEN 
		SET cancelados = cancelados + 1;
    END IF;    
	END LOOP count_estados;
    CLOSE c_estado;
    
    
    SET po_pendientes = pendientes;
    SET po_atendidos = atendidos;
    SET po_no_atendidos = no_atendidos;
    SET po_reprogramados = reprogramados;
    SET po_cancelados = cancelados;
    
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_kardex`(
	pi_cod_ped VARCHAR(10)
)
BEGIN
	SELECT 
		kardex.cod_bod,
        kardex.cod_prod,
		kardex.stk_act,
		kardex.stk_min,
        kardex.fec_act_reg,
        producto.nom_prod,
        producto.marc_prod,
        producto.prec_unit_prod
    FROM tb_detalle_pedido detalle
    inner join tb_producto producto 
		on producto.cod_prod = detalle.cod_prod
    inner join tb_kardex kardex 
		on kardex.cod_prod = detalle.cod_prod 
        and kardex.cod_bod = detalle.cod_bod
    WHERE detalle.cod_ped = pi_cod_ped;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_mensaje_data`(
	pi_codigo_hoja_ruta VARCHAR(10),
    pi_codigo_pedido VARCHAR(10)
)
BEGIN
	select
		detalle.cod_hoj_rut as codigoHojaRuta,
		detalle.cod_ped as codigoPedido,
		fn_obtener_nombre_destinatario(detalle.cod_ped) as destinatario,
		CONCAT(pedido.dir_desp_ped,' ',distrito.nom_dist) as domicilio,
        pedido.num_reserv_ped as numeroReserva,
        num_verif_ped as numeroVerificacion, 
        fec_desp_ped as fechaDespacho,
		CONCAT('de ',ventana.hor_ini_vent_hor,' a ',ventana.hor_fin_vent_hor) as rangoHorario,
        fn_obtener_numero_destinatario(detalle.cod_ped) as numero
        
    from tb_detalle_hoja_ruta detalle
    inner join tb_ventana_horaria ventana on ventana.cod_vent_hor = detalle.cod_vent_hor
    inner join tb_pedido pedido on pedido.cod_ped = detalle.cod_ped
    inner join tb_distrito distrito on distrito.cod_dist = pedido.cod_dist_desp_ped
    WHERE detalle.cod_hoj_rut = pi_codigo_hoja_ruta
    and detalle.cod_ped = pi_codigo_pedido;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_mensajes_data`(
	pi_fecha_despacho DATE
)
BEGIN
	select 
    detalle.cod_hoj_rut as codigoHojaRuta,
	detalle.cod_ped as codigoPedido,
	fn_obtener_nombre_destinatario(detalle.cod_ped) as destinatario,
	CONCAT(pedido.dir_desp_ped,' ',distrito.nom_dist) as domicilio,
    pedido.num_reserv_ped as numeroReserva,
    
    num_verif_ped as numeroVerificacion, 
    ruta.fec_desp_hoj_rut as fechaDespacho,
	CONCAT('de ',TIME_FORMAT(ventana.hor_ini_vent_hor,"%l:%i %p"),' a ',TIME_FORMAT(ventana.hor_fin_vent_hor,"%l:%i %p")) as rangoHorario,
    fn_obtener_numero_destinatario(detalle.cod_ped) as numero,
    
    -- NUEVO REQUERIMIENTO
    pedido.cod_tiend_desp as codigoTiendaDespacho,
    tienda.nom_tiend as nombreTiendaDespacho,
    unidad.num_plac_unid as unidad,
    cliente.nom_cli as clienteNombres,
    cliente.ape_cli as clienteApellidos
    
    from tb_detalle_hoja_ruta detalle
    inner join tb_hoja_ruta ruta on ruta.cod_hoj_rut = detalle.cod_hoj_rut
    inner join tb_ventana_horaria ventana on ventana.cod_vent_hor = detalle.cod_vent_hor
    inner join tb_pedido pedido on pedido.cod_ped = detalle.cod_ped
    inner join tb_distrito distrito on distrito.cod_dist = pedido.cod_dist_desp_ped
    
    -- NUEVO REQUERIMIENTO
    left join tb_tienda tienda on tienda.cod_tiend = pedido.cod_tiend_desp
    left join tb_cliente cliente on cliente.cod_cli = pedido.cod_cli
    inner join tb_unidad_chofer uc on uc.cod_unid_chof = ruta.cod_unid_chof
    inner join tb_unidad unidad on unidad.num_plac_unid = uc.num_placa_unid
    
    where ruta.fec_desp_hoj_rut = pi_fecha_despacho;
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_ruta`(
	pi_brevete_chofer VARCHAR(10),
    pi_fecha_despacho DATE
)
BEGIN
	select 
		ruta.cod_hoj_rut,
        ruta.cod_bod,
        bodega.nom_bod,
        bodega.dir_bod,
        distrito.nom_dist,
		unidad.num_plac_unid,
		unidad.num_soat_unid,
		chofer.nom_chof,
        chofer.ape_chof,
		ruta.fec_desp_hoj_rut
    
	from tb_hoja_ruta ruta
    inner join tb_unidad_chofer uc on uc.cod_unid_chof = ruta.cod_unid_chof
    inner join tb_unidad unidad on unidad.num_plac_unid = uc.num_placa_unid
    inner join tb_chofer chofer on chofer.num_brev_chof = uc.num_brev_chof
    inner join tb_bodega bodega on bodega.cod_bod = ruta.cod_bod
    inner join tb_distrito distrito on distrito.cod_dist = bodega.cod_dist
    where ruta.fec_desp_hoj_rut = pi_fecha_despacho
    and chofer.num_brev_chof = pi_brevete_chofer;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_registrar_atencion`(
	pi_codigo_hoja_ruta VARCHAR(10),
    pi_codigo_pedido VARCHAR(10),
    pi_numero_verificacion INT,
    pi_fecha_pactada_despacho DATETIME,
    pi_latitud DECIMAL(10,7),
    pi_longitud DECIMAL(10,7),
    pi_foto MEDIUMBLOB,
    OUT po_msg_cod INT,
	OUT po_msg_desc VARCHAR(50)
)
proc_label:BEGIN
	
    
	DECLARE v_verificacion INT;
    
    DECLARE exit handler for sqlexception
	BEGIN
	GET DIAGNOSTICS CONDITION 1
	@p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
    SET po_msg_cod := -1;
	SET po_msg_desc :=  CONCAT(RETURNED_SQLSTATE,' ', MESSAGE_TEXT);
    
	END;
        
    select num_verif_ped INTO v_verificacion
    from tb_pedido
    where cod_ped = pi_codigo_pedido;
    
    IF pi_numero_verificacion IS NULL OR v_verificacion <> pi_numero_verificacion THEN
    SET po_msg_cod := -1;
    SET po_msg_desc := 'Numero de verificación no válido';
    LEAVE proc_label;
	END IF;

	update tb_detalle_hoja_ruta set
	fec_pact_desp = pi_fecha_pactada_despacho,
    lat_gps_desp_ped = pi_latitud,
    long_gps_desp_ped = pi_longitud,
    fot_desp_ped = pi_foto
    
	where 
    tb_detalle_hoja_ruta.cod_hoj_rut = pi_codigo_hoja_ruta
    and tb_detalle_hoja_ruta.cod_ped = pi_codigo_pedido;
    
    SET po_msg_cod := 1;
    SET po_msg_desc := 'Numero de verificación válido';
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_registrar_detalle_pedido`(
	pi_cod_ped VARCHAR(10),
    pi_cod_prod VARCHAR(15),
    pi_cant_prod INT(11),
    pi_cod_bod VARCHAR(10)
)
BEGIN
	-- Kardex Update
    UPDATE tb_kardex SET 
		stk_act = (stk_act - pi_cant_prod),
        fec_act_reg = NOW()
	WHERE
		cod_bod = pi_cod_bod
        AND cod_prod = pi_cod_prod;

	-- Detalle Pedido Insert
	INSERT INTO tb_detalle_pedido (
			cod_ped,
			cod_prod,
			cant_prod,
			cod_bod
		) VALUES (
			pi_cod_ped,
            pi_cod_prod,
            pi_cant_prod,
            pi_cod_bod
        );
		
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_registrar_incumplimiento`(
	pi_codigo_hoja_ruta VARCHAR(10),
    pi_codigo_pedido VARCHAR(10),
    pi_codigo_motivo VARCHAR(10),
    pi_fecha_incumplimiento DATETIME,
    pi_latitud DECIMAL(10,7),
    pi_longitud DECIMAL(10,7),
    pi_foto MEDIUMBLOB
)
BEGIN
	update tb_detalle_hoja_ruta set
		fec_no_cump_desp = pi_fecha_incumplimiento,
		lat_gps_desp_ped = pi_latitud,
		long_gps_desp_ped = pi_longitud,
        cod_mot_ped = pi_codigo_motivo,
        fot_desp_ped = pi_foto
    
	where 
    tb_detalle_hoja_ruta.cod_hoj_rut = pi_codigo_hoja_ruta
    and tb_detalle_hoja_ruta.cod_ped = pi_codigo_pedido;
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_registrar_notificacion`(
	pi_codigo_hoja_ruta VARCHAR(10),
	pi_cod_ped VARCHAR(10)
)
BEGIN
	update tb_detalle_hoja_ruta 
    set flag_env_vent_hor = 1
    where cod_hoj_rut = pi_codigo_hoja_ruta
    and cod_ped = pi_cod_ped;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_reservar_pedido`(
	pi_cod_ped VARCHAR(10),

	pi_fec_sol_ped datetime,
	pi_num_reserv_ped int(11),
	pi_fec_vent_ped datetime,
	pi_fec_desp_ped date,
    
	 -- DATOS DE DESTINO
	pi_dir_desp_ped VARCHAR(100),
	pi_cod_dist_desp_ped VARCHAR(10),
    
	 -- CAMPOS PARA CLIENTE (ACTUALIZAN)
	pi_cod_cli VARCHAR(10),
	pi_nom_cli VARCHAR(50),
	pi_ape_cli VARCHAR(50),
	pi_num_dni_cli VARCHAR(8),
	pi_telf_cli VARCHAR(9),
	pi_email_cli VARCHAR(50),
	pi_dir_cli VARCHAR(100),
	pi_cod_dist VARCHAR(10),
    
	 -- CAMPOS PARA TIENDA (RETIRO O REPOSICION)
	pi_cod_tiend_desp VARCHAR(10),
	pi_fec_ret_tiend DATE

)
BEGIN
	DECLARE cliente_count INT default 0;
    DECLARE p_num_verif_ped INT(11) default null;
    
    
	
    -- CASO RETIRO EN TIENDA (REUSO DE NUMERO DE VERIFICACION)
    select num_verif_ped into p_num_verif_ped 
		from tb_pedido 
        where fec_desp_ped = pi_fec_desp_ped
        and fec_ret_tiend is not null
        and cod_tiend_desp = pi_cod_tiend_desp
        order by num_verif_ped DESC LIMIT 1;
        
	-- SI NO REUSA, SE CREA
	IF(p_num_verif_ped IS NULL) THEN
		select num_verif_ped into p_num_verif_ped from tb_pedido order by num_verif_ped DESC LIMIT 1;
    END IF;
    
    
    IF(p_num_verif_ped IS NULL) THEN 
		SET p_num_verif_ped = 1000;
    ELSE
		SET p_num_verif_ped = p_num_verif_ped + 1;
    END IF;

     -- Se inserta cliente solo si no existe
    
    SELECT COUNT(cod_cli) into cliente_count from tb_cliente where cod_cli = pi_cod_cli;
    
    IF (cliente_count > 0) THEN
    
    UPDATE tb_cliente SET 
        telf_cli = IFNULL(pi_telf_cli,telf_cli),
        email_cli = IFNULL(pi_email_cli,email_cli),
        dir_cli = IFNULL(pi_dir_cli,dir_cli),
        cod_dist = IFNULL(pi_cod_dist,cod_dist)
	WHERE cod_cli = pi_cod_cli;
    
    ELSE 
    
     INSERT INTO tb_cliente (
		cod_cli,
        nom_cli,
        ape_cli,
        num_dni_cli,
        telf_cli,
        email_cli,
        dir_cli,
        cod_dist
    ) VALUES (
		pi_cod_cli,
        pi_nom_cli,
        pi_ape_cli,
        pi_num_dni_cli,
        pi_telf_cli,
        pi_email_cli,
        pi_dir_cli,
        pi_cod_dist
    );
    
    END IF;
    
    -- Se resuelve direccion de despacho  
    IF pi_cod_tiend_desp IS NOT NULL THEN
		SELECT  
			dir_tiend,cod_dist INTO pi_dir_desp_ped,pi_cod_dist_desp_ped
		FROM tb_tienda 
		where cod_tiend = pi_cod_tiend_desp;
		

	ELSEIF pi_dir_desp_ped IS NULL THEN
		SELECT 
			dir_cli,cod_dist INTO pi_dir_desp_ped,pi_cod_dist_desp_ped
        FROM tb_cliente
		WHERE cod_cli = pi_cod_cli;
		
	END IF;

    -- Se construye pedido para cliente
	INSERT INTO tb_pedido (
		cod_ped,
        cod_cli,
        fec_sol_ped,
        num_reserv_ped,
        num_verif_ped,
        fec_vent_ped,
        fec_desp_ped,
        dir_desp_ped,
        cod_dist_desp_ped,
        cod_tiend_desp,
        fec_ret_tiend
    ) VALUES (
		pi_cod_ped,
        pi_cod_cli,
        pi_fec_sol_ped,
        pi_num_reserv_ped,
        p_num_verif_ped,
        pi_fec_vent_ped,
        pi_fec_desp_ped,
        
        pi_dir_desp_ped,
        pi_cod_dist_desp_ped,
        pi_cod_tiend_desp,
        pi_fec_ret_tiend
    );

END$$
DELIMITER ;
