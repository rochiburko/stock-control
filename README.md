# Aplicación Control de Stock

## Configuración

Para poder levantar este proyecto es necesario tener instalado Docker. Para hacerlo puede dirigirse al [link de instalacion](https://www.docker.com/get-started).

Una vez instalado, para poder levantar la base de datos, previa a levantar el proyecto debera correr el siguiente comando:
```
docker compose up -d
```
Este comando levantara un **container** de docker con un mysql linkeado al puerto **3306** del localhost.

Luego al levantar el proyecto, se inicializara la base de datos con:
- **warehouses:** AR01, AR02 y BR01
- **locations:** LM-00-00-IZ (AR01), LM-00-00-DE (AR01), LM-00-01-IZ (AR01), AL-00-00-IZ (AR02), AL-00-00-IZ (BR01)
- **stock:** MLA813727183 - Cant:10 - Ubicacion: LM-00-00-IZ (AR01)
## API Endpoints

**POST para agregar productos en una ubicación:**
http://localhost:8080/api/addProductInLocation
- Se indicará el depósito, producto, cantidad y ubicación donde se quiere agregar el producto.
```json
{
    "warehouseCode":"AR01",
    "locationCode":"LM-00-00-DE",
    "quantity":"3",
    "productId":"MLA813727183"
}
```

**POST para retirar productos en una ubicación:**
http://localhost:8080/api/removeProductFromLocation
- Se nos indicará el depósito, producto, cantidad y ubicación de donde retirar el producto.
```json
{
    "warehouseCode":"AR01",
    "locationCode":"LM-00-00-DE",
    "quantity":"2",
    "productId":"MLA813727183"
}
```

**GET de lectura:**
http://localhost:8080/api/listProductFromLocation?warehouse=AR01&location=LM-00-00-DE

**GET de busqueda:**
http://localhost:8080/api/searchProductFromWarehouse?warehouse=AR01&product=MLA813727183

## Consideraciones

Los endpoints respetan la consigna propuesta, es decir:
- Agregar productos
- Retirar productos
- Listar productos
- Buscar productos

Para todos los casos se valida que:
- La dirección tenga el patrón correcto.
- El producto/item pueda ser almacenado en los depósitos (consultado una [API pública](https://api.mercadolibre.com/items/MLA813727183)).
- No se pueden colocar más de 3 productos distintos en una ubicación.
- La suma de las cantidades de los productos que hubiera en una ubicación no
debe superar las 100 unidades.

No se desarrolló ninguna funcionalidad para gestionar depósitos o ubicaciones.
Los mismos son precargados mediante scripts de inicializacion de la API. 
Por defecto se crearon 3 depósitos con sus correspondientes ubicaciones.
Si se quisieran agregar mas, los mismos se deberán insertar directamente en la BBDD.