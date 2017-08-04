/*
 * MIT License
 *
 * Copyright (c) 2017
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package newph.core.enumeration;

import newph.core.common.iPrSkills;
import newph.core.common.SkillEntry;
import newph.core.enumeration.artifact.SpecialHeroFlag;

/**
 * Hero Type.
 *
 * @author  SerVB
 * @since   "GitHub new sources"
 */
public enum HeroType {

    KNIGHT(
            IdeologyType.IDEOLOGY_GOOD,
            SpecialHeroFlag.SHF_INVALID,
            new iPrSkills(25, 50, 5, 20),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,  5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,    4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,   4),
            new SkillEntry(FurtherSkill.FSK_POWER,     4),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE, 4),
            new SkillEntry(FurtherSkill.FSK_LOGISTICS, 20),
            new SkillEntry(FurtherSkill.FSK_SPEED,     2)
    ),

    BARBARIAN(
            IdeologyType.IDEOLOGY_EVIL,
            SpecialHeroFlag.SHF_NORANGEPENALTY,
            new iPrSkills(60, 25, 5, 10),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,  5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,    4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,   4),
            new SkillEntry(FurtherSkill.FSK_POWER,     4),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE, 4),
            new SkillEntry(FurtherSkill.FSK_RESIST,    30),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_OFFENCE, 0)
    ),

    WIZARD(
            IdeologyType.IDEOLOGY_GOOD,
            SpecialHeroFlag.SHF_SUMRESBOUNS,
            new iPrSkills(10, 10, 40, 40),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,  5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,    4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,   4),
            new SkillEntry(FurtherSkill.FSK_POWER,     6),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE, 6),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_RESIST, 0),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_OFFENCE, 0)
    ),

    WARLOCK(
            IdeologyType.IDEOLOGY_EVIL,
            SpecialHeroFlag.SHF_MANARESTORE,
            new iPrSkills(15, 10, 45, 30),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,  5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,    4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,   4),
            new SkillEntry(FurtherSkill.FSK_POWER,     6),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE, 6),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_RESIST, 0),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_OFFENCE, 0)
    ),

    SORCERESS(
            IdeologyType.IDEOLOGY_GOOD,
            SpecialHeroFlag.SHF_DMGSPBONUS,
            new iPrSkills(15, 15, 30, 40),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,  5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,    4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,   4),
            new SkillEntry(FurtherSkill.FSK_POWER,     6),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE, 6),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_RESIST, 0),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_OFFENCE, 0)
    ),

    NECROMANCER(
            IdeologyType.IDEOLOGY_EVIL,
            SpecialHeroFlag.SHF_NECRBONUS,
            new iPrSkills(15, 15, 40, 30),

            new SkillEntry(FurtherSkill.FSK_MIN_GOLD,   5000),
            new SkillEntry(FurtherSkill.FSK_ATTACK,     4),
            new SkillEntry(FurtherSkill.FSK_DEFENCE,    4),
            new SkillEntry(FurtherSkill.FSK_POWER,      4),
            new SkillEntry(FurtherSkill.FSK_KNOWLEDGE,  4),
            new SkillEntry(FurtherSkill.FSK_NECROMANCY, 15),
            new SkillEntry(/* Void skill: */FurtherSkill.FSK_OFFENCE, 0)
    );

    public final IdeologyType ideology;
    public final SpecialHeroFlag specialHeroFlag;
    public final iPrSkills prSkills;

    // ULTART_STDMODIF:
    public final SkillEntry skill0;
    public final SkillEntry skill1;
    public final SkillEntry skill2;
    public final SkillEntry skill3;
    public final SkillEntry skill4;
    public final SkillEntry skill5;
    public final SkillEntry skill6;

    private HeroType(
            final IdeologyType ideology,
            final SpecialHeroFlag specialHeroFlag,
            final iPrSkills prSkills,
            final SkillEntry skill0,
            final SkillEntry skill1,
            final SkillEntry skill2,
            final SkillEntry skill3,
            final SkillEntry skill4,
            final SkillEntry skill5,
            final SkillEntry skill6
    ) {
        this.ideology = ideology;
        this.specialHeroFlag = specialHeroFlag;
        this.prSkills = prSkills;
        this.skill0 = skill0;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.skill5 = skill5;
        this.skill6 = skill6;
    }

}
