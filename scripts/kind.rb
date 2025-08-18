require "./scripts/support"

class MakeJavaEnum
  attr_accessor :imports, :source, :name, :pkg, :kinds

  def initialize(kinds)
    @source = []
    @imports = []
    @kinds = kinds
  end

  def s(index, n)
    "0x#{n > 0 ? n : ""}#{index.to_s(16).upcase}"
  end

  def intro(n)
    [
      "    // -----------------------------------------",
      "    //                   0x#{n}",
      "    // -----------------------------------------"
    ]
  end

  def group(n)
    g = @kinds[n.to_s]
    r = intro(n)
    l = g.max_by(&:length).length
    g.each_with_index do |kind, index|
      k = "#{kind.upcase},"
      r << "    #{k.ljust( l + 2," ")}// #{s(index, n)}"
    end
    r
  end

  def cache(n)
    r = []
    r << ""
    @kinds[n.to_s].each_with_index do |kind, index|
      k = kind.upcase
      r << "        KIND_CACHE[#{s(index, n)}] = Kind.#{k};"
    end
    r
  end

  def methods(n)
    r = intro(n)
    @kinds[n.to_s].each_with_index do |kind, index|
      k = kind.upcase
      r << "    public static boolean is#{cml(k)}(byte b) { return b == #{s(index, n)}; }"
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
    source << "public enum #{@name} {"
    source << "    // -----------------------------------------"
    source << "    UNKNOWN, // [?] Generic unknown token"
    for i in @kinds.length.times
      source << group(i)
    end
    source << "    // -----------------------------------------"
    source << "    ;"
    source << "    "
    source << "    private static final Kind[] KIND_CACHE = new Kind[0x80];"
    source << "    "
    source << "    static {"
    source << "        Arrays.fill(KIND_CACHE, Kind.UNKNOWN);"
    for i in @kinds.length.times
      source << cache(i)
    end
    source << "    }"
    source << "    public static Kind is(int b) {"
    source << "        if (b < 0 || b >= KIND_CACHE.length) return Kind.UNKNOWN;"
    source << "        return KIND_CACHE[b];"
    source << "    }"
    source << ""
    for i in @kinds.length.times
      source << methods(i)
    end
    source << ""
    source << "}"

  end

  def pretty_print
    build
    source.each do |line|
      puts line
    end
  end
end

m = MakeJavaEnum.new(kinds)
m.pkg = "org.x96.sys.foundation.tokenizer.token"
m.imports << "java.util.Arrays"
m.name = "Kind"
m.pretty_print
