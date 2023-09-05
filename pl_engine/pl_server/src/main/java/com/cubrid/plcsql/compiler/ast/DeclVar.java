/*
 * Copyright (c) 2016 CUBRID Corporation.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 */

package com.cubrid.plcsql.compiler.ast;

import com.cubrid.plcsql.compiler.visitor.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class DeclVar extends DeclIdTyped {

    @Override
    public <R> R accept(AstVisitor<R> visitor) {
        return visitor.visitDeclVar(this);
    }

    public final String name;
    public final TypeSpec typeSpec;
    public final boolean notNull;
    public final Expr val;

    public DeclVar(
            ParserRuleContext ctx, String name, TypeSpec typeSpec, boolean notNull, Expr val) {
        super(ctx);

        this.name = name;
        this.typeSpec = typeSpec;
        this.notNull = notNull;
        this.val = val;
    }

    public TypeSpec typeSpec() {
        return typeSpec;
    }

    @Override
    public String kind() {
        return "variable";
    }

    @Override
    public String toJavaCode() {
        String ty = typeSpec.toJavaCode();
        if (val == null) {
            return String.format("%s[] %s = new %s[] { null };", ty, name, ty);
        } else {
            if (notNull) {
                return String.format(
                        "%s[] %s = new %s[] { checkNotNull(%s) };", ty, name, ty, val.toJavaCode());
            } else {
                return String.format("%s[] %s = new %s[] { %s };", ty, name, ty, val.toJavaCode());
            }
        }
    }
}
