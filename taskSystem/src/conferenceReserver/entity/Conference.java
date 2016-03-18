package conferenceReserver.entity;

/**
 *
 * @author shogo_saito
 */
public class Conference {
    private Long conferenceId = null;
    private String name = null;

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
