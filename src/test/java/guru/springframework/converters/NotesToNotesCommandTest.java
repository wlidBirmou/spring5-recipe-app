package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class NotesToNotesCommandTest extends TestCase {

    private NotesToNotesCommand notesToNotesCommand ;



    private final Long ID=2L;
    private final String RECIPE_NOTES="note";

    @Before
    public void setUp() throws Exception {
        this.notesToNotesCommand=new NotesToNotesCommand();
    }

    @Test
    public void testNullConvertion() {
        Notes notes = null;
        NotesCommand notesCommand = this.notesToNotesCommand.convert(notes);
    }

    @Test
    public void testIfEmpty(){
        Notes notes=Notes.builder().build();
        assertNotNull(this.notesToNotesCommand.convert(notes));
    }

    @Test
    public void testConvert() {
        Notes notes= new Notes();
        notes.setId(this.ID);
        notes.setRecipeNotes(this.RECIPE_NOTES);
        NotesCommand notesCommand=this.notesToNotesCommand.convert(notes);
        assertNotNull(notes);
        assertEquals(this.ID, notesCommand.getId());
        assertEquals(this.RECIPE_NOTES, notesCommand.getRecipeNotes());


    }
}