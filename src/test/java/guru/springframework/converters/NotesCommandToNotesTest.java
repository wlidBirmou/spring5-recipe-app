package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class NotesCommandToNotesTest extends TestCase {

    private NotesCommandToNotes notesCommandToNotes ;
    private final Long ID=2l;
    private final String RECIPE_NOTES="note";


    @Before
    public void setUp() throws Exception {
        this.notesCommandToNotes=new NotesCommandToNotes();
    }

    @Test
    public void testNullConvertion() {
        NotesCommand notesCommand = null;
        Notes notes=this.notesCommandToNotes.convert(notesCommand);
        assertNull(notes);
    }

    @Test
    public void testIfEmpty(){
        NotesCommand notesCommand=NotesCommand.builder().build();
        assertNotNull(this.notesCommandToNotes.convert(notesCommand));
    }

    @Test
    public void testConvert() {
        NotesCommand notesCommand= new NotesCommand();
        notesCommand.setId(this.ID);
        notesCommand.setRecipeNotes(this.RECIPE_NOTES);

        Notes notes=this.notesCommandToNotes.convert(notesCommand);
        assertNotNull(notes);
        assertEquals(this.ID, notes.getId());
        assertEquals(this.RECIPE_NOTES, notes.getRecipeNotes());

    }
}