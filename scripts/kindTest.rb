require "./scripts/support"

class MakeJavaKindTest
  attr_accessor :imports, :source, :name, :pkg, :kinds

  def initialize(kinds)
    @source = []
    @imports = []
    @kinds = kinds
  end

  def s(index, n)
    "0x#{n > 0 ? n : ""}#{index.to_s(16).upcase}"
  end

  def build_is_test
    # Build the main happyIs test that tests Kind.is() method
    r = []
    r << "    @Test"
    r << "    void happyIs() {"
    r << "        assertEquals(Kind.UNKNOWN, Kind.is(-3));"
    r << "        assertEquals(Kind.UNKNOWN, Kind.is(-2));"
    r << "        assertEquals(Kind.UNKNOWN, Kind.is(-1));"
    r << ""

    # Generate tests for all character codes
    @kinds.each_with_index do |(key, group), group_idx|
      group.each_with_index do |kind, index|
        k = kind.upcase
        hex_value = s(index, group_idx.to_i)
        r << "        assertEquals(Kind.#{k}, Kind.is((byte) #{hex_value}));"
      end
      r << "" if group_idx < @kinds.length - 1
    end

    r << "        assertEquals(Kind.UNKNOWN, Kind.is(128));"
    r << "        assertEquals(Kind.UNKNOWN, Kind.is(255));"
    r << "    }"
    r
  end

  def build_individual_tests
    r = []

    # Generate individual test methods for each kind
    @kinds.each_with_index do |(key, group), group_idx|
      group.each_with_index do |kind, index|
        k = kind.upcase
        method_name = cml(k)
        hex_value = s(index, group_idx.to_i)

        r << ""
        r << "    @Test"
        r << "    void happyIs#{method_name}() {"
        r << "        for (int i = 0; i < 0x80; i++) {"
        r << "            if (i == #{hex_value}) assertTrue(Kind.is#{method_name}((byte) i));"
        r << "            if (i != #{hex_value}) assertFalse(Kind.is#{method_name}((byte) i));"
        r << "        }"
        r << "    }"
      end
    end

    r
  end

  def build
    if pkg
      source << "package #{pkg};"
    end
    source << ""
    for i in imports
      source << "import #{i};"
    end
    source << ""
    source << "public class #{@name} {"

    # Add the main happyIs test
    source << build_is_test

    # Add individual tests for each method
    source << build_individual_tests

    source << "}"
  end

  def pretty_print
    build
    source.flatten.each do |line|
      puts line
    end
  end
end

m = MakeJavaKindTest.new(kinds)
m.pkg = "org.x96.sys.foundation.tokenizer.token"
m.imports << "static org.junit.jupiter.api.Assertions.*"
m.imports << "org.junit.jupiter.api.Test"
m.name = "KindTest"
m.pretty_print
