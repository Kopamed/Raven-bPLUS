package keystrokesmod.tweaker.transformers;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

public class TransformerGuiIngame_optForge implements Transformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.gui.GuiIngame", "net.minecraftforge.client.GuiIngameForge"};
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void transform(ClassNode classNode, String transformedName) {
        classNode.methods.forEach(methodNode -> {
            String mappedMethodName = mapMethodName(classNode, methodNode);
            if (mappedMethodName.equalsIgnoreCase("renderGameOverlay") || mappedMethodName.equalsIgnoreCase("func_175180_a")) {
                AbstractInsnNode[] arr = methodNode.instructions.toArray();
                int i = 0;

                MethodInsnNode color_FFFF_V = null;
                MethodInsnNode disableLighting_0_V = null;
                MethodInsnNode enableAlpha_0_V = null;

                while (i < arr.length) {
                    if (arr[i].getOpcode() == INVOKESTATIC && arr[i] instanceof MethodInsnNode) {
                        MethodInsnNode currentMethodInsnNode = ((MethodInsnNode)arr[i]);
                        if ((currentMethodInsnNode.name.equalsIgnoreCase("color") || currentMethodInsnNode.name.equalsIgnoreCase("func_179131_c"))
                            && currentMethodInsnNode.desc.equals("(FFFF)V")) {
                            // LOCATED : INVOKESTATIC net/minecraft/client/renderer/GlStateManager.color (FFFF)V
                            color_FFFF_V = currentMethodInsnNode;
                            i++;

                            while (i < arr.length && arr[i].getOpcode() != INVOKESTATIC) {
                                i++;
                            }

                            if (arr[i].getOpcode() == INVOKESTATIC && arr[i] instanceof MethodInsnNode) {
                                currentMethodInsnNode = (MethodInsnNode) arr[i];

                                if ((currentMethodInsnNode.name.equalsIgnoreCase("disableLighting") || currentMethodInsnNode.name.equalsIgnoreCase("func_179140_f"))
                                    && currentMethodInsnNode.desc.equals("()V")) {
                                    // LOCATED : INVOKESTATIC net/minecraft/client/renderer/GlStateManager.disableLighting ()V
                                    disableLighting_0_V = currentMethodInsnNode;
                                    i++;

                                    while (i < arr.length && arr[i].getOpcode() != INVOKESTATIC) {
                                        i++;
                                    }

                                    if (arr[i].getOpcode() == INVOKESTATIC && arr[i] instanceof MethodInsnNode) {
                                        currentMethodInsnNode = (MethodInsnNode) arr[i];

                                        if ((currentMethodInsnNode.name.equalsIgnoreCase("enableAlpha") || currentMethodInsnNode.name.equalsIgnoreCase("func_179141_d"))
                                                && currentMethodInsnNode.desc.equals("()V")) {
                                            // LOCATED : INVOKESTATIC net/minecraft/client/renderer/GlStateManager.disableLighting ()V
                                            enableAlpha_0_V = currentMethodInsnNode;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    color_FFFF_V = null;
                    disableLighting_0_V = null;
                    enableAlpha_0_V = null;
                    i++;
                }

                if (color_FFFF_V != null && disableLighting_0_V != null && enableAlpha_0_V != null) {
                    methodNode.instructions.insertBefore(color_FFFF_V, getInsnList());
                }
            }
        });
    }

    private InsnList getInsnList() {
        InsnList insnList = new InsnList();
        insnList.insert(new MethodInsnNode(INVOKESTATIC, "keystrokesmod/NotificationRenderer", "render", "()V", false));
        return insnList;
    }
}
