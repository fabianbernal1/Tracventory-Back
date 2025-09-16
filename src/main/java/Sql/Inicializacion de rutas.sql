-- Menú raíz
INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), 'fa-tachometer-alt', 'Dashboard', '/', true, NULL);

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), 'fa-cogs', 'Administración', NULL, true, NULL);

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), 'fa-warehouse', 'Inventario', NULL, true, NULL);

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), 'fa-exchange-alt', 'Transacciones', NULL, true, NULL);

-- Administración hijos
INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-user', 'Usuarios', '/users', true,
       (SELECT id_form FROM public.forms WHERE name = 'Administración'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-user-shield', 'Roles', '/roles', true,
       (SELECT id_form FROM public.forms WHERE name = 'Administración'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-users', 'Perfiles', '/profiles', true,
       (SELECT id_form FROM public.forms WHERE name = 'Administración'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-file-alt', 'Formularios', '/forms', true,
       (SELECT id_form FROM public.forms WHERE name = 'Administración'));

-- Inventario hijos
INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-box', 'Productos', '/products', true,
       (SELECT id_form FROM public.forms WHERE name = 'Inventario'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-tags', 'Categorías', '/categories', true,
       (SELECT id_form FROM public.forms WHERE name = 'Inventario'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-palette', 'Colores', '/colors', true,
       (SELECT id_form FROM public.forms WHERE name = 'Inventario'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-store', 'Tiendas', '/stores', true,
       (SELECT id_form FROM public.forms WHERE name = 'Inventario'));

-- Transacciones hijos
INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-shopping-cart', 'Movimientos', '/transactions', true,
       (SELECT id_form FROM public.forms WHERE name = 'Transacciones'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-random', 'Orígenes de Movimientos', '/transactionOrigins', true,
       (SELECT id_form FROM public.forms WHERE name = 'Transacciones'));

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id)
VALUES (nextval('form_seq'), 'fa-exchange-alt', 'Tipos de Movimientos', '/transactionTypes', true,
       (SELECT id_form FROM public.forms WHERE name = 'Transacciones'));

-- Formularios no visibles en el sidebar
INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), NULL , 'Stocks', '/stocks', false , NULL);

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), NULL , 'Variaciones de productos', '/product-variations', false , NULL);

INSERT INTO public.forms (id_form, icon, name, url, visible, parent_form_id) VALUES
(nextval('form_seq'), NULL , 'Permisos', '/permissions', false , NULL);
       
-- Dar permisos completos al perfil 2 en todos los forms
INSERT INTO permissions (form_pms, profile_pms, c, r, u, d)
SELECT id_form, 2, true, true, true, true
FROM forms;
