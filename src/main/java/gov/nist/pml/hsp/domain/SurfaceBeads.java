package gov.nist.pml.hsp.domain;

import java.util.Collections;
import java.util.List;

/**
 * This class doesn't provide any modifiers.
 * In other words, this is an immutable object.
 *
 * @ThreadSafe
 */
public class SurfaceBeads {

    private List<Bead> beads;

    public SurfaceBeads(List<Bead> beads) {
        if (beads == null || beads.isEmpty()) {
            this.beads = Collections.emptyList();
        } else {
            this.beads = Collections.unmodifiableList(beads);
        }
    }

    public List<Bead> getBeads() {
        return beads;
    }
}
