package net.sf.jabref.model.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import net.sf.jabref.logic.util.MetadataSerializationConfiguration;
import net.sf.jabref.model.entry.FieldName;
import net.sf.jabref.model.strings.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Select explicit bibtex entries. It is also known as static group.
 *
 * @author jzieren
 */
public class ExplicitGroup extends SimpleKeywordGroup {

    public static final String ID = "ExplicitGroup:";

    private final List<String> legacyEntryKeys = new ArrayList<>();

    private static final Log LOGGER = LogFactory.getLog(ExplicitGroup.class);


    public ExplicitGroup(String name, GroupHierarchyType context, Character keywordSeparator) {
        super(name, context, FieldName.GROUPS, name, true, keywordSeparator);
    }

    public void addLegacyEntryKey(String key) {
        this.legacyEntryKeys.add(key);
    }

    @Override
    public AbstractGroup deepCopy() {
        ExplicitGroup copy = new ExplicitGroup(getName(), getContext(), keywordSeparator);
        copy.legacyEntryKeys.addAll(legacyEntryKeys);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExplicitGroup)) {
            return false;
        }
        ExplicitGroup other = (ExplicitGroup) o;
        return Objects.equals(getName(), other.getName()) && Objects.equals(getHierarchicalContext(),
                other.getHierarchicalContext()) && Objects.equals(getLegacyEntryKeys(), other.getLegacyEntryKeys());
    }

    /**
     * Returns a String representation of this group and its entries.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExplicitGroup.ID).append(
                StringUtil.quote(getName(), MetadataSerializationConfiguration.GROUP_UNIT_SEPARATOR, MetadataSerializationConfiguration.GROUP_QUOTE_CHAR)).
                append(MetadataSerializationConfiguration.GROUP_UNIT_SEPARATOR).append(getContext().ordinal()).append(MetadataSerializationConfiguration.GROUP_UNIT_SEPARATOR);

        // write legacy entry keys in well-defined order for CVS compatibility
        Set<String> sortedKeys = new TreeSet<>();
        sortedKeys.addAll(legacyEntryKeys);

        for (String sortedKey : sortedKeys) {
            sb.append(StringUtil.quote(sortedKey, MetadataSerializationConfiguration.GROUP_UNIT_SEPARATOR, MetadataSerializationConfiguration.GROUP_QUOTE_CHAR)).append(
                    MetadataSerializationConfiguration.GROUP_UNIT_SEPARATOR);
        }
        return sb.toString();
    }

    /**
     * Remove all stored cite keys, resulting in an empty group.
     */
    public void clearLegacyEntryKeys() {
        legacyEntryKeys.clear();
    }

    public List<String> getLegacyEntryKeys() {
        return legacyEntryKeys;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}
