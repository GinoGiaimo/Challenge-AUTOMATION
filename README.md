# OrdenCompraTest

## Descripción
El archivo `OrdenCompraTest.java` contiene pruebas automatizadas para verificar el flujo de compra en la aplicación DemoBlaze. Estas pruebas aseguran que los usuarios puedan seleccionar un producto, agregarlo al carrito y completar el proceso de compra correctamente.

## Funcionalidades probadas
- Verificar que el producto seleccionado se agrega correctamente al carrito.
- Validar que el precio total en el carrito coincide con el precio del producto seleccionado.
- Completar el flujo de compra y confirmar que se realiza exitosamente.

## Estructura del Test
El test sigue los siguientes pasos principales:
1. **Selección del producto**: Se selecciona un producto específico desde la página principal.
2. **Validación del carrito**: Se verifica que el producto seleccionado está presente en el carrito y que el precio total es correcto.
3. **Finalización de la compra**: Se completa el proceso de compra y se valida el resultado.

## Dependencias
- **Selenium WebDriver**: Para la automatización de la interacción con el navegador.
- **TestNG**: Framework utilizado para la ejecución y organización de las pruebas.
- **Log4j**: Para el registro de logs durante la ejecución de las pruebas.

## Archivos relacionados
- **Page Objects**:
  - `DemoBlazeHomePage.java`: Representa la página principal de la aplicación.
  - `DemoBlazeProductoPage.java`: Representa la página de detalles del producto.
  - `DemoBlazeCarritoPage.java`: Representa la página del carrito de compras.
- **DriverFactory.java**: Clase para la configuración y manejo del WebDriver.

## Ejecución de las pruebas
1. Asegúrate de que las dependencias necesarias estén instaladas y configuradas correctamente.
2. Ejecuta el archivo `testng.xml` ubicado en la carpeta `testSuites` para correr las pruebas.

```bash
mvn test -Dsuite=testng.xml
```

## Notas adicionales
- Asegúrate de que el navegador y el WebDriver estén actualizados para evitar problemas de compatibilidad.
- Verifica que la aplicación DemoBlaze esté accesible antes de ejecutar las pruebas.
