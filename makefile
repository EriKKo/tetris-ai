BUILDDIR = build
TEMPDIR := $(shell mktemp -u --tmpdir=. -t tmp.XXXXXXX)

default:
	find src -name "*.java" > $(TEMPDIR)
	rm -rf $(BUILDDIR)
	mkdir $(BUILDDIR)
	javac -d $(BUILDDIR) @$(TEMPDIR)
	rm $(TEMPDIR)
