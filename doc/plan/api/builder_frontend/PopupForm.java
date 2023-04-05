public interface PopupForm<T> {
    void displayForm(T obj);
    boolean validateInput();
    void saveInputToObject(T obj);

    default List<Field> getObjectFields(T obj) {
        Class<?> clazz = obj.getClass();
        return Arrays.asList(clazz.getDeclaredFields());
    }

    default Map<String, Object> getFieldValues(T obj) {
        Map<String, Object> fieldMap = new HashMap<>();
        List<Field> fields = getObjectFields(obj);

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                fieldMap.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fieldMap;
    }

    default void setFieldValues(T obj, Map<String, Object> fieldMap) {
        List<Field> fields = getObjectFields(obj);

        for (Field field : fields) {
            if (fieldMap.containsKey(field.getName())) {
                try {
                    field.setAccessible(true);
                    Object value = fieldMap.get(field.getName());
                    field.set(obj, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // additional default methods will exist to set id, create the javafx, etc
}
