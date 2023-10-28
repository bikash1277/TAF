package endpoints;

public enum Endpoints {
    NEW("/new"),
    SHUFFLE("/shuffle"),
    DRAW("/draw"),
    DECKID("/{deck_id}");

    private String endpoint;

    private Endpoints(String s) {
        this.endpoint = s;

    }

    public String getEndPoint(String s) {
        return this.endpoint;
    }
}
