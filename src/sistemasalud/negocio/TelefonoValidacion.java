package sistemasalud.negocio;

public final class TelefonoValidacion {

    private TelefonoValidacion() {
    }

    public static void validar(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }

        String valor = telefono.trim();
        if (!valor.matches("\\+?[0-9().\\s-]+")) {
            throw new IllegalArgumentException(
                    "El teléfono solo puede contener números y separadores comunes.");
        }
        int primerDigito = valor.charAt(0) == '+' ? 1 : 0;
        if (primerDigito == valor.length() || !Character.isDigit(valor.charAt(primerDigito))) {
            throw new IllegalArgumentException("El formato del teléfono no es válido.");
        }

        int digitos = 0;
        int profundidadParentesis = 0;
        boolean parentesisConDigitos = false;
        for (int i = 0; i < valor.length(); i++) {
            char caracter = valor.charAt(i);
            if (Character.isDigit(caracter)) {
                digitos++;
                if (profundidadParentesis > 0) {
                    parentesisConDigitos = true;
                }
            } else if (caracter == '(') {
                if (profundidadParentesis > 0) {
                    throw new IllegalArgumentException(
                            "El formato de los paréntesis del teléfono no es válido.");
                }
                profundidadParentesis++;
                parentesisConDigitos = false;
            } else if (caracter == ')') {
                if (profundidadParentesis == 0 || !parentesisConDigitos) {
                    throw new IllegalArgumentException(
                            "El formato de los paréntesis del teléfono no es válido.");
                }
                profundidadParentesis--;
            }
        }

        if (digitos < 7 || digitos > 15) {
            throw new IllegalArgumentException(
                    "El teléfono debe contener entre 7 y 15 dígitos.");
        }
        if (profundidadParentesis != 0
                || !Character.isDigit(valor.charAt(valor.length() - 1))) {
            throw new IllegalArgumentException("El formato del teléfono no es válido.");
        }
    }
}
