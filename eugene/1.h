Biobrick BBa_B1102(.ID("BBa_B1102"),.DISPLAYID("8049"), .Description("null"), .SEQUENCES("cgcaaaaaaccccgcttcggcggggttttttcgctactagagaaagaggagaaatactagatggcttcctccgaagacgttatcaaagagttcatgcgtttcaaagttcgtatggaaggttccgttaacggtcacgagttcgaaatcgaaggtgaaggtgaaggtcgtccgtacgaaggtacccagaccgctaaactgaaagttaccaaaggtggtccgctgccgttcgcttgggacatcctgtccccgcagttccagtacggttccaaagcttacgttaaacacccggctgacatcccggactacctgaaactgtccttcccggaaggtttcaaatgggaacgtgttatgaacttcgaagacggtggtgttgttaccgttacccaggactcctccctgcaagacggtgagttcatctacaaagttaaactgcgtggtaccaacttcccgtccgacggtccggttatgcagaaaaaaaccatgggttgggaagcttccaccgaacgtatgtacccggaagacggtgctctgaaaggtgaaatcaaaatgcgtctgaaactgaaagacggtggtcactacgacgctgaagttaaaaccacctacatggctaaaaaaccggttcagctgccgggtgcttacaaaaccgacatcaaactggacatcacctcccacaacgaagactacaccatcgttgaacagtacgaacgtgctgaaggtcgtcactccaccggtgcttaataacgctgatagtgctagtgtagatcgctactagagccaggcatcaaataaaacgaaaggctcagtcgaaagactgggcctttcgttttatctgttgtttgtcggtgaacgctctctactagagtcacactggctcaccttcgggtgggcctttctgcgtttata"), .TYPE("Composite"));
Property sequence(txt);
Property name(txt);
Property ID(txt);
DISPLAYID(num);
Property Description(txt);
Property SEQUENCES(txt);
Property TYPE(txt);
Part Biobrick(ID, DISPLAYID, Description,SEQUENCES,TYPE);
Part Subpart(ID, DISPLAYID, Description,TYPE);
Subpart BBa_B1002(.ID("BBa_B1002"), .DISPLAYID("7328"), .Description("Terminator (artificial, small, %T~=85%)"), .TYPE("Terminator"));
Subpart BBa_B0034(.ID("BBa_B0034"), .DISPLAYID("151"), .Description("RBS (Elowitz 1999) -- defines RBS efficiency"), .TYPE("RBS"));
Subpart BBa_E1010(.ID("BBa_E1010"), .DISPLAYID("4932"), .Description(" **highly** engineered mutant of red fluorescent protein from Discosoma striata (coral)"), .TYPE("Coding"));
Subpart BBa_B0010(.ID("BBa_B0010"), .DISPLAYID("603"), .Description("T1 from E. coli rrnB"), .TYPE("Terminator"));
Subpart BBa_B0012(.ID("BBa_B0012"), .DISPLAYID("145"), .Description("TE from coliphageT7"), .TYPE("Terminator"));
Subpart BBa_B1002(.ID("BBa_B1002"), .DISPLAYID("7328"), .Description("Terminator (artificial, small, %T~=85%)"), .TYPE("Terminator"));
Subpart BBa_I13507(.ID("BBa_I13507"), .DISPLAYID("5461"), .Description("Screening plasmid intermediate"), .TYPE("Composite"));
print(8049.name)