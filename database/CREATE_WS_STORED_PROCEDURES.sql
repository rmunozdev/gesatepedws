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
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_detalle_ruta`(
	pi_codigo_ruta VARCHAR(10)
)
BEGIN
    select 
		detalle.ord_desp_ped as orden,
		detalle.cod_ped as _pedido,
		fn_obtener_nombre_destinatario(detalle.cod_ped) as destinatario,
		CONCAT(pedido.dir_desp_ped,' ',distrito.nom_dist) as domicilio,
		CONCAT('De ' + ventana.hor_fin_vent_hor,' a ',ventana.hor_fin_vent_hor) as horario,
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_obtener_ruta`(
	pi_brevete_chofer VARCHAR(10),
    pi_fecha_despacho DATE
)
BEGIN
	select 
		ruta.cod_hoj_rut,
        ruta.cod_bod,
		unidad.num_plac_unid,
		unidad.num_soat_unid,
		chofer.nom_chof,
        chofer.ape_chof,
		ruta.fec_desp_hoj_rut
    
	from tb_hoja_ruta ruta
    inner join tb_unidad_chofer uc on uc.cod_unid_chof = ruta.cod_unid_chof
    inner join tb_unidad unidad on unidad.num_plac_unid = uc.num_placa_unid
    inner join tb_chofer chofer on chofer.num_brev_chof = uc.num_brev_chof
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
    select num_verif_ped INTO v_verificacion
    from tb_pedido
    where cod_ped = pi_codigo_pedido;
    
    IF v_verificacion IS NOT NULL AND v_verificacion <> pi_numero_verificacion THEN
    SET po_msg_cod := -1;
    SET po_msg_desc := 'Numero de verificación no válido';
    LEAVE proc_label;
	END IF;

	SET po_msg_cod := 1;
    SET po_msg_desc := 'Numero de verificación válido';
    
	update tb_detalle_hoja_ruta set
	fec_pact_desp = pi_fecha_pactada_despacho,
    lat_gps_desp_ped = pi_latitud,
    long_gps_desp_ped = pi_longitud,
    fot_desp_ped = pi_foto
    
	where 
    tb_detalle_hoja_ruta.cod_hoj_rut = pi_codigo_hoja_ruta
    and tb_detalle_hoja_ruta.cod_ped = pi_codigo_pedido;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ws_registrar_incumplimiento`(
	pi_codigo_hoja_ruta VARCHAR(10),
    pi_codigo_pedido VARCHAR(10),
    pi_codigo_motivo VARCHAR(10),
    pi_fecha_pactada_despacho DATE,
    pi_latitud DECIMAL(10,7),
    pi_longitud DECIMAL(10,7),
    pi_foto MEDIUMBLOB
)
BEGIN
	update tb_detalle_hoja_ruta set
		fec_pact_desp = pi_fecha_pactada_despacho,
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
    DECLARE p_num_verif_ped INT(11) default 0;
    select num_verif_ped into p_num_verif_ped from tb_pedido order by num_verif_ped DESC LIMIT 1;
	
    IF(p_num_verif_ped IS NULL) THEN 
		SET p_num_verif_ped = 1000;
    ELSE
		SET p_num_verif_ped = p_num_verif_ped + 1;
    END IF;
    
     -- Se inserta cliente solo si no existe
    
    SELECT COUNT(cod_cli) into cliente_count from tb_cliente where cod_cli = pi_cod_cli;
    
    IF (cliente_count > 0) THEN
    
    UPDATE tb_cliente SET 
		nom_cli = IFNULL(pi_nom_cli,nom_cli),
        ape_cli = IFNULL(pi_ape_cli,ape_cli),
        num_dni_cli = IFNULL(pi_num_dni_cli,num_dni_cli),
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
