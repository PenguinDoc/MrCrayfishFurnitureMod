/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHedge extends Block
{
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	protected static final AxisAlignedBB[] BOUNDING_BOX = new AxisAlignedBB[] { new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.1875, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.0, 0.0, 0.1875, 0.8125, 1.0, 1.0), new AxisAlignedBB(0.1875, 0.0, 0.0, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.0, 0.8125, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.0, 0.0, 0.0, 0.8125, 1.0, 1.0), new AxisAlignedBB(0.1875, 0.0, 0.1875, 1.0, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.1875, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.1875, 1.0, 1.0, 0.8125), new AxisAlignedBB(0.0, 0.0, 0.1875, 1.0, 1.0, 1.0), new AxisAlignedBB(0.1875, 0.0, 0.0, 1.0, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.0, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.8125), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) };

	private static final AxisAlignedBB COLLISION_BOX_CENTER = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.5, 0.8125);
	private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.8125, 0.0, 0.1875, 1.0, 1.5, 0.8125);
	private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.8125, 0.0, 0.1875, 1.0, 1.5, 0.8125);
	private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.8125, 0.0, 0.1875, 1.0, 1.5, 0.8125);
	private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.8125, 0.0, 0.1875, 1.0, 1.5, 0.8125);

	public BlockHedge(Material material) {
		super(Material.LEAVES);
		this.setHardness(1.0F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX[getBoundingBoxId(this.getActualState(state, source, pos))];
	}

	private static int getBoundingBoxId(IBlockState state)
	{
		int i = 0;

		if (state.getValue(NORTH)) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (state.getValue(EAST)) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (state.getValue(SOUTH)) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (state.getValue(WEST)) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
	{
		if (state.getValue(NORTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_NORTH);
		}

		if (state.getValue(EAST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_EAST);
		}

		if (state.getValue(SOUTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_SOUTH);
		}

		if (state.getValue(WEST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_WEST);
		}

		addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_CENTER);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, isHedge(world, pos.north())).withProperty(EAST, isHedge(world, pos.east())).withProperty(SOUTH, isHedge(world, pos.south())).withProperty(WEST, isHedge(world, pos.west()));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
	}

	private boolean isHedge(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() instanceof BlockHedge || world.getBlockState(pos).getBlock().isNormalCube(world.getBlockState(pos), world, pos);
	}
}
