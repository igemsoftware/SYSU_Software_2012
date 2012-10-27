//*************************
// Define the Design Space
//*************************

Property Name(txt);
Property Sequence(txt);
Property Represses(txt);
Property RepressedBy(txt);

Part Promoter(Name, Sequence);

print("**** CONSTITUTIVE PROMOTERS ****");
Part ConstitutivePromoter(Name, Sequence);
ConstitutivePromoter J23100 = Genbank.import(ConstitutivePromoter, "J23100");
ConstitutivePromoter J23101 = Genbank.import(ConstitutivePromoter, "J23101");
ConstitutivePromoter J23102 = Genbank.import(ConstitutivePromoter, "J23102");
ConstitutivePromoter J23103 = Genbank.import(ConstitutivePromoter, "J23103");
ConstitutivePromoter J23104 = Genbank.import(ConstitutivePromoter, "J23104");
ConstitutivePromoter J23105 = Genbank.import(ConstitutivePromoter, "J23105");
ConstitutivePromoter J23106 = Genbank.import(ConstitutivePromoter, "J23106");

print("**** INDUCIBLE PROMOTERS ****");
Part InduciblePromoter(Name, Sequence);
InduciblePromoter pBad = Genbank.import(InduciblePromoter, "I13453");


print("**** REPRESSIBLE PROMOTERS ****");
Part RepressiblePromoter(Name, Sequence, RepressedBy);
RepressiblePromoter pCI = Genbank.import(RepressiblePromoter, "R0051");


print("**** REPRESSORS ****");
Part Repressor(Name, Sequence, Represses);
Repressor cI = Genbank.import(Repressor, "C0051");
//cI.Represses("BBa_R0051");
Repressor LuxR = Genbank.import(Repressor, "C0062"); 
Repressor AraC = Genbank.import(Repressor, "I0500");

print("**** RBSs ****");
Part RBS (Name, Sequence);
RBS rbs61100 = Genbank.import(RBS, "J61100");
RBS rbs61101 = Genbank.import(RBS, "J61101");
RBS rbs61102 = Genbank.import(RBS, "J61102");
RBS rbs61103 = Genbank.import(RBS, "J61103");
RBS rbs61104 = Genbank.import(RBS, "J61104");
RBS rbs61105 = Genbank.import(RBS, "J61105");
RBS rbs61106 = Genbank.import(RBS, "J61106");
RBS rbs61107 = Genbank.import(RBS, "J61107");


print("**** REPORTERS ****");
Part Reporter(Name, Sequence);
Reporter GFP = Genbank.import(Reporter, "I13522");
Reporter YFP = Genbank.import(Reporter, "E0430");

print("**** TERMINATORS ****");
Part Terminator(Name, Sequence);
Terminator T1 = Genbank.import(Terminator, "J61048");
Terminator B0010 = Genbank.import(Terminator, "B0010");