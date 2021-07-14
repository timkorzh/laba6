package collection_objects.StudyGroupField;

import collection_objects.Location;
import collection_objects.StudyGroup;

public enum StudyGroupField {
    NAME("gName", (src, dst) -> dst.setName(src.getName())),
    COORDINATES("gCoords", (src, dst) -> {
        dst.getCoordinates().setX(src.getCoordinates().getX());
        dst.getCoordinates().setY(src.getCoordinates().getY());
    } ),
    STUDENTS_COUNT("stCount", (src, dst) ->
            dst.setStudentsCount(src.getStudentsCount())),
    FORM_OF_EDUCATION("foedu", (src, dst) ->
            dst.setFormOfEducation(src.getFormOfEducation())),
    SEMESTER("sem", (src, dst) ->
            dst.setSemesterEnum(src.getSemesterEnum())),
    ADMIN_NAME("aName", (src, dst) ->
            dst.getGroupAdmin().setName(src.getGroupAdmin().getName())),
    ADMIN_PASSPORT("passport", (src, dst) ->
            dst.getGroupAdmin().setPassportID(src.getGroupAdmin().getPassportID())),
    ADMIN_LOCATION("aCoords", (src, dst) ->
            dst.getGroupAdmin().setLocation(new Location(src.getGroupAdmin().getLocation())));

    private final String scriptName;
    private final DataCopier dataCopier;

    private interface DataCopier {
        void copyData(StudyGroup srcGroup, StudyGroup dstGroup);
    }

    StudyGroupField(String scriptName, DataCopier dataCopier) {
        this.scriptName = scriptName;
        this.dataCopier = dataCopier;
    }

    public String getScriptName() { return scriptName; }

    public static StudyGroupField getField(String scriptName)
                                          throws IllegalArgumentException {
        for (StudyGroupField field : StudyGroupField.values()) {
            if  (field.getScriptName().equals(scriptName))
                return field;
        }
        throw new IllegalArgumentException("scriptName " + scriptName +
                " не соответствует ни одному из полей StudyGroup");
    }

    public void copyValue(StudyGroup srcGroup, StudyGroup dstGroup) {
        dataCopier.copyData(srcGroup, dstGroup);
    }

    public static void copyValue(StudyGroup srcGroup, StudyGroup dstGroup, String scriptNames) {
        for (String scriptName : scriptNames.split(" ")) {
            try {
                getField(scriptName).copyValue(srcGroup, dstGroup);
            } catch (IllegalArgumentException ignore) {}
        }
        /*
        for(StudyGroupField field : StudyGroupField.values()) {
            Pattern p = Pattern.compile("-" + field.getScriptName() + " -|$");
            Matcher m = p.matcher(scriptNames);
            if (m.find()) {
                field.copyValue(srcGroup, dstGroup);
            }
        }
        */
    }
}
